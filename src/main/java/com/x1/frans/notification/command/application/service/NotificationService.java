package com.x1.frans.notification.command.application.service;

import com.x1.frans.approval.command.domain.aggregate.ApprovalEntity;
import com.x1.frans.approval.command.domain.aggregate.ApprovalLineEntity;
import com.x1.frans.approval.command.domain.repository.ApprovalCommandRepository;
import com.x1.frans.approval.command.domain.repository.ApprovalLineCommandRepository;
import com.x1.frans.approval.common.ApprovalLineType;
import com.x1.frans.exception.AccessDeniedException;
import com.x1.frans.exception.AllMessagesAlreadyReadException;
import com.x1.frans.exception.ApprovalNotFoundException;
import com.x1.frans.exception.NoDeletableMessagesException;
import com.x1.frans.exception.NoPermissionOrNoneExist;
import com.x1.frans.exception.UnreadMessagesCannotBeDeletedException;
import com.x1.frans.exception.UserNotFoundException;
import com.x1.frans.notification.command.domain.model.NotificationDomainType;
import com.x1.frans.notification.command.domain.repository.EmitterRepository;
import com.x1.frans.notification.command.domain.aggregate.Notification;
import com.x1.frans.notification.command.dto.NotificationDTO;
import com.x1.frans.notification.command.domain.repository.NotificationRepository;
import com.x1.frans.notification.command.domain.model.NotificationTarget;
import com.x1.frans.notification.command.domain.model.NotificationType;
import com.x1.frans.order.command.domain.vo.OrderStatus;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.UserCommandRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    // SSE 연결 지속 시간 설정(성능 테스트 후 리팩토링 예정)
    private static final Long DEFAULT_TIMEOUT = 30L * 1000 * 60;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private static final String EVENT_NAME = "sse";

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;
    private final UserCommandRepository userCommandRepository;
    private final ApprovalLineCommandRepository approvalLineCommandRepository;
    private final ApprovalCommandRepository approvalCommandRepository;

    private String generateEmitterId(String userIdStr) {
        return userIdStr + "_" + UUID.randomUUID();
    }

    // 코드 일관성과 가동성을 위한 처리, NPE 방지
    private String toUserIdStr(Long userId) {
        return String.valueOf(userId);
    }

    /* 설명.
     *  1. 로그인 정보로 부터 받은 userId(Long)
     *  2. 내부에서 String으로 변환
     *  3. 문자열을 사용해서 Emitter ID 생성 및 저장소 접근
    * */
    public SseEmitter subscribe(Long userId, String lastEventId) {
        if (userId == null) {
            throw new AccessDeniedException("SSE 연결은 인증된 사용자만 가능합니다.");
        }

        String userIdStr = String.valueOf(userId);
        String emitterId = generateEmitterId(userIdStr);

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(emitterId, emitter);

        emitter.onCompletion(() -> safeDeleteEmitter(emitterId));
        emitter.onTimeout(() -> safeDeleteEmitter(emitterId));
        emitter.onError((e) -> {
            log.warn("SSE 연결 오류 발생 - emitterId={}, error={}", emitterId, e.getMessage());
            safeDeleteEmitter(emitterId);
        });

        String eventId = generateEmitterId(userIdStr);
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + userIdStr + "]");

        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, userIdStr, emitterId, emitter);
        }

        return emitter;
    }

    private void safeDeleteEmitter(String emitterId) {
        if (emitterRepository.exists(emitterId)) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name(EVENT_NAME)
                    .data(data)
            );
            log.info("[알림 전송 성공] emitterId={}, eventId={}", emitterId, eventId);
        } catch (IOException exception) {
            log.warn("SSE 전송 실패 (IOException) - emitterId={}, eventId={}, exceptionType={}, message={}",
                    emitterId, eventId, exception.getClass().getName(), exception.getMessage(), exception);
            safeDeleteEmitter(emitterId);
        } catch (Exception ex) {
            log.error("알 수 없는 SSE 전송 예외 발생 - emitterId={}, eventId={}, exception={}",
                    emitterId, eventId, ex.getMessage(), ex);
            safeDeleteEmitter(emitterId);
        }
    }

    // NPE 방지
    private boolean hasLostData(String lastEventId) {
        return lastEventId != null && !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, String userIdStr, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserId(userIdStr);
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry ->
                        sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    @Transactional
    public void send(UserEntity receiver, NotificationType notificationType, NotificationTarget target) {
        try {
            Notification notification = notificationRepository.save(
                    createNotification(receiver, notificationType, target)
            );

            // SSE 전송은 트랜잭션 외부에서 처리
            String receiverIdStr = String.valueOf(receiver.getId());
            String eventId = generateEmitterId(receiverIdStr);

            // 트랜잭션 완료 후 SSE 전송
            sendSseNotificationAsync(receiverIdStr, eventId, notification);

        } catch (Exception e) {
            log.error("알림 생성 중 에러: receiverId={}, type={}", receiver.getId(), notificationType, e);
            throw e;
        }
    }

    @Async
    public void sendSseNotificationAsync(String receiverIdStr, String eventId, Notification notification) {
        try {
            Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiverIdStr);
            log.info("[알림 PUSH] userId={}, emitter 개수={}", receiverIdStr, emitters.size());

            // ConcurrentModificationException 방지
            Map<String, SseEmitter> emittersCopy = new HashMap<>(emitters);

            emittersCopy.forEach((key, emitter) -> {
                try {
                    log.info("[알림 PUSH] emitterId={}", key);
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotificationDTO.Response.createResponse(notification));
                } catch (Exception e) {
                    log.error("개별 emitter 전송 실패: emitterId={}", key, e);
                    emitterRepository.deleteById(key);
                }
            });
        } catch (Exception e) {
            log.error("SSE 알림 전송 중 에러: receiverId={}", receiverIdStr, e);
        }
    }

    private Notification createNotification(UserEntity receiver,
                                            NotificationType notificationType,
                                            NotificationTarget target) {
        String content = notificationType.getMessage();
        return Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .content(notificationType.getMessage())
                .target(target)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Transactional
    public List<NotificationDTO.Response> getNotification(UserEntity user) {
        return notificationRepository.findByReceiverId(user.getId())
                .stream()
                .map(NotificationDTO.Response::createResponse)
                .toList();
    }

    @Transactional
    public void markAsRead(Long notificationId, UserEntity receiver) {
        Notification notification = notificationRepository
                .findByIdAndReceiverId(notificationId, receiver.getId())
                .orElseThrow(() -> new NoPermissionOrNoneExist("알림이 존재하지 않거나 권한이 없습니다."));

        if (!notification.isRead()) {
            notification.markAsRead();
            notificationRepository.save(notification);
        }
    }

    @Transactional
    public void markAllAsRead(UserEntity receiver) {
        List<Notification> unreadNotifications
                = notificationRepository.findByReceiverIdAndIsReadFalse(receiver.getId());

        if (unreadNotifications.isEmpty()) {
            throw new AllMessagesAlreadyReadException("모든 메시지를 읽은 상태입니다.");
        }

        unreadNotifications.forEach(Notification::markAsRead);
        notificationRepository.saveAll(unreadNotifications);
    }

    @Transactional
    public void deleteAllRead(UserEntity user) {
        List<Notification> readNotifications = notificationRepository.findByReceiverIdAndIsReadTrue(user.getId());

        if (readNotifications.isEmpty()) {
            throw new NoDeletableMessagesException("삭제 가능한 메시지가 없습니다.");
        }

        notificationRepository.deleteAll(readNotifications);

        // 이벤트 캐시에서도 모두 삭제
        String userIdStr = String.valueOf(user.getId());
        for (Notification n : readNotifications) {
            emitterRepository.deleteEventCacheByNotificationId(userIdStr, n.getId());
        }
    }

    @Transactional
    public void deleteReadNotification(Long id, UserEntity user) {
        Notification notification = notificationRepository.findByIdAndReceiverId(id, user.getId())
                .orElseThrow(() -> new NoPermissionOrNoneExist("알림이 존재하지 않거나 권한이 없습니다."));

        if (!notification.isRead()) {
            throw new UnreadMessagesCannotBeDeletedException("읽지 않은 알림은 삭제할 수 없습니다.");
        }

        notificationRepository.delete(notification);

        // 이벤트 캐시에서도 해당 알림 삭제
        String userIdStr = String.valueOf(user.getId());
        emitterRepository.deleteEventCacheByNotificationId(userIdStr, id);
    }

    public void clearEmittersAndCache(UserEntity user) {
        String userIdStr = String.valueOf(user.getId());
        emitterRepository.deleteAllEmitterStartWithId(userIdStr);
        emitterRepository.deleteAllEventCacheStartWithId(userIdStr);
        log.info("SSE 연결 및 캐시 삭제 완료 - userId={}", userIdStr);
    }

    @Scheduled(fixedRate = 30000)
    public void sendHeartbeat() {
        // 실제 연결된 emitter만 조회하도록 수정
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId("");

        if (emitters.isEmpty()) {
            log.debug("[HEARTBEAT] 연결된 emitter 없음");
            return;
        }

        log.info("[HEARTBEAT] 전체 emitter 개수: {}", emitters.size());

        // ConcurrentModificationException 방지를 위해 복사본 사용
        Map<String, SseEmitter> emittersCopy = new HashMap<>(emitters);

        emittersCopy.forEach((emitterId, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .id("heartbeat-" + System.currentTimeMillis())
                        .name("heartbeat")
                        .data("ping"));
                log.debug("[HEARTBEAT] 전송 성공: {}", emitterId);
            } catch (IOException e) {
                log.warn("[HEARTBEAT] 전송 실패(연결 끊김): {}", emitterId, e);
                emitterRepository.deleteById(emitterId);
            } catch (Exception e) {
                log.error("[HEARTBEAT] 예상치 못한 에러: {}", emitterId, e);
                emitterRepository.deleteById(emitterId);
            }
        });
    }

    @Transactional
    public void createOrderStatusNotification(Long orderId, OrderStatus status, Long receiverId) {

        UserEntity receiver = userCommandRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException("수신자(가맹점주)를 찾을 수 없습니다. id=" + receiverId));

        NotificationTarget target = new NotificationTarget(
                NotificationDomainType.ORDER,
                orderId,
                "/franchise?tab=주문관리&orderId=" + orderId
        );

        NotificationType notificationType = NotificationType.ORDER_RESPONSE;

        send(receiver, notificationType, target);

        log.info("주문 상태 변경 알림 생성 및 발송: orderId={}, status={}, receiverId={}", orderId, status, receiverId);
    }


    @Transactional
    public void createApprovalLineNotification(Long approvalId, Long receiverId, NotificationTarget target) {

        List<ApprovalLineEntity> approvalLines = approvalLineCommandRepository.findByApprovalId(approvalId);

        approvalLines.stream()
                .filter(line ->
                        (line.getApprovalType() == ApprovalLineType.APPROVER || line.getApprovalType()
                                                == ApprovalLineType.COOPERATOR)
                        && line.getUser().getId().equals(receiverId))
                .findFirst()
                .ifPresent(line -> {
                    UserEntity receiver = line.getUser();

                    send(receiver, NotificationType.APPROVAL_REQUEST, target);
                });

    }

    @Transactional
    public void createApprovalRejectedNotification(Long approvalId, Long receiverId, NotificationTarget target) {

        ApprovalEntity approvalEntity = approvalCommandRepository.findById(approvalId)
                .orElseThrow(() -> new ApprovalNotFoundException("해당 결재를 찾을 수 없습니다."));

        UserEntity drafter = approvalEntity.getUser();

        send(drafter, NotificationType.APPROVAL_REJECTED, target);

        log.info("결재 반려 알림 전송: approvalId={}, drafterId={}", approvalId, drafter.getId());
    }

}
