package com.x1.frans.user.command.repository;

import com.x1.frans.user.command.aggregate.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCommandRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByCode(String code);
}
