package com.x1.frans.user.command.repository;

import com.x1.frans.user.command.aggregate.HqUserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HqUserDetailCommandRepository extends JpaRepository<HqUserDetailEntity, Long> {
    Optional<HqUserDetailEntity> findByUser(com.x1.frans.user.command.aggregate.UserEntity user);
}
