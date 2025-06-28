package com.x1.frans.statistics.query.view.service;

import com.x1.frans.statistics.query.view.dto.FranchiseOrderAmountQueryDTO;
import java.util.List;

public interface FranchiseOrderAmountQueryService {

    List<FranchiseOrderAmountQueryDTO> getMonthlyStatsByManager(Long userId, Integer year, Integer month);

    List<FranchiseOrderAmountQueryDTO> getMonthlyStatsByDepartment(Long deptId, Integer year, Integer month);

    List<FranchiseOrderAmountQueryDTO> getMonthlyStatsForAllByDuty(Long userId, Long departmentId, Long dutyId);

    List<FranchiseOrderAmountQueryDTO> getMonthlyStatsByManagerByFranchiseId(Long userId, Long franchiseId);

    List<FranchiseOrderAmountQueryDTO> getStatsByOwnerId(Long userId, Integer year, Integer month);

}
