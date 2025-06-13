package com.x1.frans.returns.command.domain.repository;

import com.x1.frans.returns.command.domain.aggregate.ReturnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnCommandRepository extends JpaRepository<ReturnEntity, Long> {

}
