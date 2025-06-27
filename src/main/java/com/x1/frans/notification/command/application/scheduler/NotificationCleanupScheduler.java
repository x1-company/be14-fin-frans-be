//package com.x1.frans.notification.command.application.scheduler;
//
//import com.x1.frans.notification.command.domain.repository.NotificationRepository;
//import io.swagger.v3.oas.annotations.Operation;
//import java.time.LocalDateTime;
//import java.util.List;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class NotificationCleanupScheduler {
//
//    private final NotificationRepository notificationRepository;
//
//    public NotificationCleanupScheduler(NotificationRepository notificationRepository) {
//        this.notificationRepository = notificationRepository;
//    }
//
//    @Scheduled(cron = "0 0 * * * *")
//    @Operation(
//            summary = "읽은 알림 자동 삭제",
//            description = "읽은 지 24시간이 지난 알림을 매시간마다 자동으로 삭제합니다."
//    )
//    public void cleanupReadNotifications() {
//        LocalDateTime expireTime = LocalDateTime.now().minusHours(24);
//        int batchSize = 500;
//
//        while (true) {
//            List<Long> ids = notificationRepository.
//                    findIdsToDelete(expireTime, org.springframework.data.domain.PageRequest.of(0, batchSize));
//            if (ids.isEmpty()) break;
//
//            notificationRepository.deleteByIds(ids);
//        }
//    }
//
//}
