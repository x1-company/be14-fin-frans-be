package com.x1.frans.franchise.query.service;

import com.x1.frans.franchise.query.dto.FranchiseListDTO;
import com.x1.frans.franchise.query.repository.FranchiseQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FranchiseQueryServiceImpl implements FranchiseQueryService {

    private final FranchiseQueryMapper franchiseQueryMapper;

    @Autowired
    public FranchiseQueryServiceImpl(FranchiseQueryMapper franchiseQueryMapper) {
        this.franchiseQueryMapper = franchiseQueryMapper;
    }

    @Override
    public String findLatestCodeByPrefixAndYearMonth(String codePrefix) {
        return franchiseQueryMapper.findLatestCodeByCodePrefix(codePrefix);
    }

    @Override
    public List<FranchiseListDTO> findAllFranchise() {
        return franchiseQueryMapper.findAllFranchise();
    }
}
