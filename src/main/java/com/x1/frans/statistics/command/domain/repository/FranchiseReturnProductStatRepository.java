package com.x1.frans.statistics.command.domain.repository;

import com.x1.frans.statistics.command.domain.aggregate.FranchiseReturnProductStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseReturnProductStatRepository extends JpaRepository <FranchiseReturnProductStat, Long> {
}
