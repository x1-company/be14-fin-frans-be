package com.x1.frans.notification.command.application.controller;

import com.x1.frans.exception.UserNotFoundException;
import com.x1.frans.notification.command.application.service.NotificationService;
import com.x1.frans.notification.command.dto.NotificationDTO;
import com.x1.frans.notification.command.dto.NotificationDTO.Response;
import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.UserCommandRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("api/notification")
@Tag(name = "🔔 알림", description = "notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserCommandRepository userCommandRepository;

    @Autowired
    public NotificationController(NotificationService notificationService,
                                  UserCommandRepository userCommandRepository) {
        this.notificationService = notificationService;
        this.userCommandRepository = userCommandRepository;
    }

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @Operation(
            summary = "SSE 구독 연결",
            description = "클라이언트가 SSE로 알림을 수신할 수 있도록 서버와 연결합니다. "
                                + "마지막 이벤트 ID를 이용해 누락된 이벤트를 다시 전송할 수 있습니다."
    )
    public SseEmitter subscribe(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
                                String lastEventId) {

        return notificationService.subscribe(customUserDetails.getUserId(), lastEventId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Response>> getNotifications(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            UserEntity user = userCommandRepository.findById(customUserDetails.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("사용자 정보를 찾을 수 없습니다."));
            List<NotificationDTO.Response> notifications = notificationService.getNotification(user);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            throw e; // 예외를 다시 던져서 Spring이 처리하도록
        }
    }

    @PatchMapping("/{id}/read")
    @Operation(
            summary = "특정 알림 읽음 처리",
            description = "알림 ID를 기준으로 해당 알림을 읽음 처리합니다."
    )
    public ResponseEntity<Void> markAsRead(
                                @PathVariable Long id,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        UserEntity user = userCommandRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보를 찾을 수 없습니다."));
        notificationService.markAsRead(id, user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/read-all")
    @Operation(
            summary = "전체 알림 읽음 처리",
            description = "로그인한 사용자의 모든 알림을 읽음 처리합니다."
    )
    public ResponseEntity<Void> markAllAsRead(
                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        UserEntity user = userCommandRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보를 찾을 수 없습니다."));
        notificationService.markAllAsRead(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/read-all")
    @Operation(
            summary = "읽음 처리된 전체 알림 삭제",
            description = "로그인한 사용자의 알림 중 읽음 처리된 모든 알림을 삭제합니다."
    )
    public ResponseEntity<Void> deleteAllReadNotifications(
                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        UserEntity user = userCommandRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보를 찾을 수 없습니다."));
        notificationService.deleteAllRead(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{id}")
    @Operation(
            summary = "특정 알림 삭제",
            description = "알림 ID에 해당하는 읽은 알림을 삭제합니다."
    )
    public ResponseEntity<Void> deleteReadNotification(
                                @PathVariable Long id,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        UserEntity user = userCommandRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보를 찾을 수 없습니다."));
        notificationService.deleteReadNotification(id, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/disconnect")
    @Operation(
            summary = "SSE 연결 및 이벤트 캐시 제거",
            description = "사용자의 SSE 연결 및 이벤트 캐시를 명시적으로 제거합니다. 로그아웃 전 호출하면 좋습니다."
    )
    public ResponseEntity<Void> disconnectSse(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        UserEntity user = userCommandRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보를 찾을 수 없습니다."));

        notificationService.clearEmittersAndCache(user);
        return ResponseEntity.ok().build();
    }


}
