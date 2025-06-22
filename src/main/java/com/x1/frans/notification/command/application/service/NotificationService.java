package com.x1.frans.notification.command.application.service;

import com.x1.frans.approval.command.domain.aggregate.ApprovalEntity;
import com.x1.frans.approval.command.domain.aggregate.ApprovalLineEntity;
import com.x1.frans.approval.command.domain.repository.ApprovalLineCommandRepository;
import com.x1.frans.approval.common.ApprovalLineType;
import com.x1.frans.exception.AllMessagesAlreadyReadException;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
public class NotificationService {

    // SSE 연결 지속 시간 설정(성능 테스트 후 리팩토링 예정)
    private static final Long DEFAULT_TIMEOUT = 30L * 1000 * 60;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private static final String EVENT_NAME = "sse";

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;
    private final UserCommandRepository userCommandRepository;
    private final ApprovalLineCommandRepository approvalLineCommandRepository;

    @Autowired
    public NotificationService(EmitterRepository emitterRepository, NotificationRepository notificationRepository,
                               UserCommandRepository userCommandRepository,
                               ApprovalLineCommandRepository approvalLineCommandRepository) {
        this.emitterRepository = emitterRepository;
        this.notificationRepository = notificationRepository;
        this.userCommandRepository = userCommandRepository;
        this.approvalLineCommandRepository = approvalLineCommandRepository;
    }

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
        String userIdStr = toUserIdStr(userId);

        String emitterId = generateEmitterId(userIdStr);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> safeDeleteEmitter(emitterId));
        emitter.onTimeout(() -> safeDeleteEmitter(emitterId));

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

    // 메서드 파라미터 구성방식 수정
    // 변경 전: 낱개 인자로 직접 받았음. 변경 후: NotificationTarget 객체를 직접 인자로 받음
    public void send(UserEntity receiver, NotificationType notificationType, NotificationTarget target) {
        Notification notification = notificationRepository.save(
                createNotification(receiver, notificationType, target)
        );

        String receiverIdStr = String.valueOf(receiver.getId());
        String eventId = generateEmitterId(receiverIdStr); // UUID 기반으로 변경
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiverIdStr);

        emitters.forEach((key, emitter) -> {
            emitterRepository.saveEventCache(key, notification);
            sendNotification(emitter, eventId, key, NotificationDTO.Response.createResponse(notification));
        });
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

    public List<NotificationDTO.Response> getNotification(UserEntity user) {
        return notificationRepository.findByReceiverId(user.getId())
                .stream()
                .map(NotificationDTO.Response::createResponse)
                .toList();
    }

    public void markAsRead(Long notificationId, UserEntity receiver) {
        Notification notification = notificationRepository
                .findByIdAndReceiverId(notificationId, receiver.getId())
                .orElseThrow(() -> new NoPermissionOrNoneExist("알림이 존재하지 않거나 권한이 없습니다."));

        if (!notification.isRead()) {
            notification.markAsRead();
            notificationRepository.save(notification);
        }
    }

    // 증긴에 예외 발생 시 DB 상태가 중간 단계로 남아 일관성 깨질 수 있음
    // 동시성 문제로 인해 예상치 못한 데이터 꼬임 가능
    // 롤백 불가로 인해 데이터 무결성 훼손될 수 있음
    // => @Transactional 사용
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
    }

    @Transactional
    public void deleteReadNotification(Long id, UserEntity user) {
        Notification notification = notificationRepository.findByIdAndReceiverId(id, user.getId())
                .orElseThrow(() -> new NoPermissionOrNoneExist("알림이 존재하지 않거나 권한이 없습니다."));

        if (!notification.isRead()) {
            throw new UnreadMessagesCannotBeDeletedException("읽지 않은 알림은 삭제할 수 없습니다.");
        }

        notificationRepository.delete(notification);
    }

    public void clearEmittersAndCache(UserEntity user) {
        String userIdStr = user.getEmail();

        emitterRepository.deleteAllEmitterStartWithId(userIdStr);
        emitterRepository.deleteAllEventCacheStartWithId(userIdStr);

        log.info("SSE 연결 및 캐시 삭제 완료 - userId={}", userIdStr);
    }

    public void createOrderStatusNotification(Long orderId, OrderStatus status, Long receiverId) {

        UserEntity receiver = userCommandRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException("수신자(가맹점주)를 찾을 수 없습니다. id=" + receiverId));

        NotificationTarget target = new NotificationTarget(
                NotificationDomainType.ORDER,
                orderId,

                "status=" + status.name()
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

                    log.info("결재 알림 전송: approvalId={}, receiverId={}", approvalId, receiverId);
                });
    }


}
