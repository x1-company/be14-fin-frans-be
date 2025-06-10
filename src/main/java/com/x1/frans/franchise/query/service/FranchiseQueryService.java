package com.x1.frans.franchise.query.service;

import com.x1.frans.franchise.query.dto.FranchiseDetailDTO;
import com.x1.frans.franchise.query.dto.FranchiseListDTO;

import java.util.List;

public interface FranchiseQueryService {
    String findLatestCodeByPrefixAndYearMonth(String codePrefix);

    List<FranchiseListDTO> findAllFranchise();

    FranchiseDetailDTO findFranchiseDetailById(Long franchiseId);
}
