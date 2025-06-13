package com.x1.frans.statistics.query.view.service;

import com.x1.frans.exception.StatisticsUnauthorizedAccessException;
import com.x1.frans.statistics.query.view.dto.FranchiseOrderAmountQueryDTO;
import com.x1.frans.statistics.query.view.repository.FranchiseOrderAmountMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FranchiseOrderAmountQueryServiceImpl implements FranchiseOrderAmountQueryService {

    private final FranchiseOrderAmountMapper franchiseOrderAmountMapper;

    @Autowired
    public FranchiseOrderAmountQueryServiceImpl(FranchiseOrderAmountMapper franchiseOrderAmountMapper) {
        this.franchiseOrderAmountMapper = franchiseOrderAmountMapper;
    }

    @Override
    public List<FranchiseOrderAmountQueryDTO> getMonthlyStatsByManager(Long userId) {

        boolean hasFranchises = franchiseOrderAmountMapper.existsFranchiseByManagerId(userId);
        if (!hasFranchises) {
            return List.of();
        }

        return franchiseOrderAmountMapper.selectStatsByManager(userId);
    }

    @Override
    public List<FranchiseOrderAmountQueryDTO> getMonthlyStatsByDepartment(Long deptId) {
        boolean hasFranchises = franchiseOrderAmountMapper.existsFranchiseByDepartmentId(deptId);

        if (!hasFranchises) {
            return List.of();
        }

        return franchiseOrderAmountMapper.selectStatsByDepartment(deptId);
    }

    @Override
    public List<FranchiseOrderAmountQueryDTO> getMonthlyStatsForAllByDuty(Long userId, Long departmentId, Long dutyId) {

        String dutyName = franchiseOrderAmountMapper.selectDutyNameById(dutyId);
        if (!"팀장".equals(dutyName)) {
            throw new StatisticsUnauthorizedAccessException("전체 가맹점 통계는 팀장만 조회할 수 있습니다.");
        }

        String deptCode = franchiseOrderAmountMapper.selectDeptCodeById(departmentId);
        if (deptCode == null || !deptCode.startsWith("DPT_SAL")) {
            throw new StatisticsUnauthorizedAccessException("영업 부서 소속 팀장만 조회할 수 있습니다.");
        }

        return franchiseOrderAmountMapper.selectAllStats();
    }

}
