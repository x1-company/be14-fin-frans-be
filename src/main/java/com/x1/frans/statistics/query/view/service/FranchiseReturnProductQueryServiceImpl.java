package com.x1.frans.statistics.query.view.service;

import com.x1.frans.exception.StatisticsUnauthorizedAccessException;
import com.x1.frans.statistics.query.view.dto.FranchiseReturnProductQueryDTO;
import com.x1.frans.statistics.query.view.repository.FranchiseReturnProductMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FranchiseReturnProductQueryServiceImpl implements FranchiseReturnProductQueryService {

    private final FranchiseReturnProductMapper franchiseReturnProductMapper;

    @Autowired
    public FranchiseReturnProductQueryServiceImpl(FranchiseReturnProductMapper franchiseReturnProductMapper) {
        this.franchiseReturnProductMapper = franchiseReturnProductMapper;
    }

    @Override
    public List<FranchiseReturnProductQueryDTO> getMonthlyStatsByManager(Long userId, Integer year, Integer month) {

        boolean hasFranchises = franchiseReturnProductMapper.existsFranchiseByManagerId(userId);
        if (!hasFranchises) {
            return List.of();
        }

        return franchiseReturnProductMapper.selectStatsByManager(userId, year, month);
    }

    @Override
    public List<FranchiseReturnProductQueryDTO> getMonthlyStatsByManagerByFranchiseId(Long userId, Long franchiseId) {
        boolean hasFranchises = franchiseReturnProductMapper.existsFranchiseByManagerId(userId);
        if (!hasFranchises) {
            return List.of();
        }

        return franchiseReturnProductMapper.selectStatsByManagerByFranchiseId(userId, franchiseId);
    }

    @Override
    public List<FranchiseReturnProductQueryDTO> getMonthlyStatsByDepartment(Long deptId, Integer year, Integer month) {

        boolean hasFranchises = franchiseReturnProductMapper.existsFranchiseByDepartmentId(deptId);

        if (!hasFranchises) {
            return List.of();
        }

        return franchiseReturnProductMapper.selectStatsByDepartment(deptId, year, month);
    }


    @Override
    public List<FranchiseReturnProductQueryDTO> getMonthlyStatsForAllByDuty(Long userId, Long deptId, Long dutyId) {

        String dutyName = franchiseReturnProductMapper.selectDutyNameById(dutyId);
        if (!"팀장".equals(dutyName)) {
            throw new StatisticsUnauthorizedAccessException("전체 가맹점 통계는 팀장만 조회할 수 있습니다.");
        }

        String deptCode = franchiseReturnProductMapper.selectDeptCodeById(deptId);
        if (deptCode == null || !deptCode.startsWith("DPT_SAL")) {
            throw new StatisticsUnauthorizedAccessException("영업 부서 소속 팀장만 조회할 수 있습니디.");
        }

        return franchiseReturnProductMapper.selectAllStats();
    }

    @Override
    public List<FranchiseReturnProductQueryDTO> getStatsByOwnerId(Long userId, Integer year, Integer month) {
        boolean hasFranchises = franchiseReturnProductMapper.existsFranchiseByOwnerId(userId);
        if (!hasFranchises) {
            return List.of();
        }

        return franchiseReturnProductMapper.selectStatsByOwner(userId, year, month);
    }

    @Override
    public List<FranchiseReturnProductQueryDTO> getMonthlyStatsByDepartmentByFranchiseId(Long deptId, Long franchiseId) {
        boolean hasFranchises = franchiseReturnProductMapper.existsFranchiseByDepartmentId(deptId);
        if (!hasFranchises) {
            return List.of();
        }

        return franchiseReturnProductMapper.selectStatsByDepartmentByFranchiseId(deptId, franchiseId);
    }
}
