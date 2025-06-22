package com.x1.frans.user.common;

import com.x1.frans.exception.UserNotFoundException;
import com.x1.frans.notification.command.application.service.NotificationService;
import com.x1.frans.notification.command.domain.model.NotificationTarget;
import com.x1.frans.notification.command.domain.model.NotificationType;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.UserCommandRepository;
import com.x1.frans.user.query.repository.UserQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DepartmentNotificationHelper {

    private final UserQueryMapper userQueryMapper;
    private final UserCommandRepository userCommandRepository;
    private final NotificationService notificationService;

    public void notify(List<Long> parentDepartmentIds, NotificationType type, NotificationTarget target) {
        List<Long> userIds = userQueryMapper.findHqUserIdsByParentDepartmentIds(parentDepartmentIds);

        for (Long userId : userIds) {
            UserEntity receiver = userCommandRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("알림 수신자 없음: id=" + userId));

            notificationService.send(receiver, type, target);
        }
    }

}
