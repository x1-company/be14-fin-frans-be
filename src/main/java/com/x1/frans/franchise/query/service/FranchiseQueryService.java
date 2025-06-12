package com.x1.frans.franchise.query.service;

import com.x1.frans.franchise.query.dto.FranchiseDetailDTO;
import com.x1.frans.franchise.query.dto.FranchiseListDTO;
import com.x1.frans.user.enums.UserType;

import java.util.List;

public interface FranchiseQueryService {
    String findLatestCodeByPrefixAndYearMonth(String codePrefix);

    List<FranchiseListDTO> findFranchisesByDepartmentId(Long userId);

    FranchiseDetailDTO findHqFranchiseDetailById(Long franchiseId, Long userId);

    FranchiseDetailDTO findFranchiseDetailById(Long franchiseId, Long userId);

    List<FranchiseListDTO> findFranchisesByManagerId(Long userId);

    List<FranchiseListDTO> findFranchisesByOwnerId(Long userId, UserType userType);
}
