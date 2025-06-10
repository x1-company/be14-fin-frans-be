package com.x1.frans.franchise.query.repository;

import com.x1.frans.franchise.query.dto.FranchiseDetailDTO;
import com.x1.frans.franchise.query.dto.FranchiseListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FranchiseQueryMapper {
    String findLatestCodeByCodePrefix(String codePrefix);

    List<FranchiseListDTO> findAllFranchise();

    FranchiseDetailDTO findFranchiseDetailById(@Param("franchiseId") Long franchiseId);
}
