package com.x1.frans.franchise.command.repository;

import com.x1.frans.franchise.command.aggregate.FranchiseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseCommandRepository extends JpaRepository<FranchiseEntity, Integer> {
}
