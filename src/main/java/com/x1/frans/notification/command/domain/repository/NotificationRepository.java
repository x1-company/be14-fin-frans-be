package com.x1.frans.notification.command.domain.repository;

import com.x1.frans.notification.command.domain.aggregate.Notification;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByReceiverId(Long receiverId);

    Optional<Notification> findByIdAndReceiverId(Long id, Long receiverId);

    @Transactional
    @Modifying
    @Query("delete from Notification n where n.readAt is not null and n.createdAt <= :expireTime")
    void deleteReadNotificationsOlderThan(@Param("expireTime") LocalDateTime expireTime);

    List<Notification> findByReceiverIdAndIsReadFalse(Long id);

    List<Notification> findByReceiverIdAndIsReadTrue(Long id);

}
