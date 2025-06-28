package com.x1.frans.statistics.query.view.service;

import com.x1.frans.statistics.query.view.dto.FranchiseProductOrderQueryDTO;

import java.util.List;

public interface FranchiseProductOrderQueryService {
    List<FranchiseProductOrderQueryDTO> getStats(long franchiseId, int year, int month);

    List<FranchiseProductOrderQueryDTO> getMonthlyStatsByManager(Long userId, Integer year, Integer month);

    List<FranchiseProductOrderQueryDTO> getMonthlyStatsByManagerByFranchiseId(Long userId, Long franchiseId);

    List<FranchiseProductOrderQueryDTO> getStatsByOwnerId(Long userId, Integer year, Integer month);
}
