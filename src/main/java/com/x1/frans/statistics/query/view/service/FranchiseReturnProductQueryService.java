package com.x1.frans.statistics.query.view.service;

import com.x1.frans.statistics.query.view.dto.FranchiseReturnProductQueryDTO;
import java.util.List;

public interface FranchiseReturnProductQueryService {

    List<FranchiseReturnProductQueryDTO> getMonthlyStatsByManager(Long userId, Integer year, Integer month);


    List<FranchiseReturnProductQueryDTO> getMonthlyStatsByDepartment(Long deptId, Integer year, Integer month);

    List<FranchiseReturnProductQueryDTO> getMonthlyStatsForAllByDuty(Long userId, Long deptId, Long dutyId);

    List<FranchiseReturnProductQueryDTO> getMonthlyStatsByManagerByFranchiseId(Long userId, Long franchiseId);

    List<FranchiseReturnProductQueryDTO> getStatsByOwnerId(Long userId, Integer year, Integer month);

    List<FranchiseReturnProductQueryDTO> getMonthlyStatsByDepartmentByFranchiseId(Long deptId, Long franchiseId);

}
