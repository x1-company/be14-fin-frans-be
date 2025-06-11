package com.x1.frans.franchise.query.service;

import com.x1.frans.franchise.query.dto.FranchiseDetailDTO;
import com.x1.frans.franchise.query.dto.FranchiseListDTO;

import java.util.List;

public interface FranchiseQueryService {
    String findLatestCodeByPrefixAndYearMonth(String codePrefix);

    List<FranchiseListDTO> findFranchisesByDepartmentId(Long userId);

    FranchiseDetailDTO findFranchiseDetailById(Long franchiseId);

    List<FranchiseListDTO> findFranchisesByManagerId(Long userId);
}
