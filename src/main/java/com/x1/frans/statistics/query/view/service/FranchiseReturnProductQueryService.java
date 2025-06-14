package com.x1.frans.statistics.query.view.service;

import com.x1.frans.statistics.query.view.dto.FranchiseReturnProductQueryDTO;
import java.util.List;

public interface FranchiseReturnProductQueryService {

    List<FranchiseReturnProductQueryDTO> getMonthlyStatsByManager(Long userId);


    List<FranchiseReturnProductQueryDTO> getMonthlyStatsByDepartment(Long deptId);

    List<FranchiseReturnProductQueryDTO> getMonthlyStatsForAllByDuty(Long userId, Long deptId, Long dutyId);

}
