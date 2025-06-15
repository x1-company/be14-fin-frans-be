package com.x1.frans.statistics.command.domain.repository;

import com.x1.frans.statistics.command.domain.aggregate.FranchiseProductOrderStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseProductOrderStatRepository extends JpaRepository<FranchiseProductOrderStat, Long> {
}
