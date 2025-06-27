package com.x1.frans.notification.command.domain.repository;

import com.x1.frans.notification.command.domain.aggregate.Notification;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByReceiverId(Long receiverId);

    Optional<Notification> findByIdAndReceiverId(Long id, Long receiverId);

    List<Notification> findByReceiverIdAndIsReadFalse(Long id);

    List<Notification> findByReceiverIdAndIsReadTrue(Long id);

    @Query("select n.id from Notification n where n.readAt is not null and n.createdAt <= :expireTime")
    List<Long> findIdsToDelete(
            @Param("expireTime") LocalDateTime expireTime,
            org.springframework.data.domain.Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Notification n where n.id in :ids")
    void deleteByIds(@Param("ids") List<Long> ids);

}
