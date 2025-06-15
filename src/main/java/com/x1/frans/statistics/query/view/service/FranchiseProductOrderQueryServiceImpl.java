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
}
