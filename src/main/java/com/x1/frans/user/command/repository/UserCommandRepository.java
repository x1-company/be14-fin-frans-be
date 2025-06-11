package com.x1.frans.user.command.repository;

import com.x1.frans.user.command.aggregate.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCommandRepository extends JpaRepository<UserEntity, Long> {

    @Modifying
    @Query("UPDATE UserEntity u SET u.isLocked = TRUE WHERE u.code = :userCode")
    void accountLock(@Param("userCode") String userCode);
}
