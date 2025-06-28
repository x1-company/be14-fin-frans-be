package com.x1.frans.returns.command.domain.repository;

import com.x1.frans.returns.command.domain.aggregate.ReturnDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnDetailCommandRepository extends JpaRepository<ReturnDetailEntity, Long> {

    List<ReturnDetailEntity> findByReturnEntityId(Long returnId);

}
