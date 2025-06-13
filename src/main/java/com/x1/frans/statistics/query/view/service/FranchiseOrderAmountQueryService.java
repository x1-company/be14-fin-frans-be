package com.x1.frans.statistics.query.view.service;

import com.x1.frans.statistics.query.view.dto.FranchiseOrderAmountQueryDTO;
import java.util.List;

public interface FranchiseOrderAmountQueryService {

    List<FranchiseOrderAmountQueryDTO> getMonthlyStatsByManager(Long userId);

    List<FranchiseOrderAmountQueryDTO> getMonthlyStatsByDepartment(Long deptId);

    List<FranchiseOrderAmountQueryDTO> getMonthlyStatsForAllByDuty(Long userId, Long departmentId, Long dutyId);

}
