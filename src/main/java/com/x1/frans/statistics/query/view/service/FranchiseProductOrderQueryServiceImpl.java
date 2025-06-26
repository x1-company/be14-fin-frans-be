package com.x1.frans.statistics.query.view.service;

import com.x1.frans.statistics.query.view.dto.FranchiseProductOrderQueryDTO;
import com.x1.frans.statistics.query.view.repository.FranchiseProductOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FranchiseProductOrderQueryServiceImpl implements FranchiseProductOrderQueryService {

    private final FranchiseProductOrderMapper franchiseProductOrderMapper;

    @Autowired
    public FranchiseProductOrderQueryServiceImpl (FranchiseProductOrderMapper franchiseProductOrderMapper) {
        this.franchiseProductOrderMapper = franchiseProductOrderMapper;
    }

    @Override
    public List<FranchiseProductOrderQueryDTO> getStats(long franchiseId, int year, int month) {

        return franchiseProductOrderMapper.getStats(franchiseId, year, month);
    }

    @Override
    public List<FranchiseProductOrderQueryDTO> getMonthlyStatsByManager(Long userId, Integer year, Integer month) {
        boolean hasFranchises = franchiseProductOrderMapper.existsFranchiseByManagerId(userId);
        if (!hasFranchises) {
            return List.of();
        }
        return franchiseProductOrderMapper.selectStatsByManager(userId, year, month);  // ← userId 그대로 전달
    }

    @Override
    public List<FranchiseProductOrderQueryDTO> getMonthlyStatsByManagerByFranchiseId(Long userId, Long franchiseId) {  // ← year, month 제거
        boolean hasFranchises = franchiseProductOrderMapper.existsFranchiseByManagerId(userId);
        if (!hasFranchises) {
            return List.of();
        }
        return franchiseProductOrderMapper.selectStatsByManagerByFranchiseId(userId, franchiseId);  // ← year, month 제거
    }

}
