package com.x1.frans.statistics.command.domain.repository;

import com.x1.frans.statistics.command.domain.aggregate.FranchiseOrderAmountStat;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseOrderAmountStatRepository extends JpaRepository<FranchiseOrderAmountStat, Long> {

    FranchiseOrderAmountStat findByFranchiseIdAndYearAndMonth(
            @NotNull Long franchiseId, @NotNull Integer year, @NotNull Integer month);

}

