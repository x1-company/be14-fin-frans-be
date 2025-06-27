package com.x1.frans.franchise.query.repository;

import com.x1.frans.franchise.query.dto.FranchiseDetailDTO;
import com.x1.frans.franchise.query.dto.FranchiseListDTO;
import com.x1.frans.franchise.query.dto.MyInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FranchiseQueryMapper {
    String findLatestCodeByCodePrefix(String codePrefix);

    List<FranchiseListDTO> findFranchisesByDepartmentId(@Param("userId") Long userId);

    List<FranchiseListDTO> findFranchisesByManagerId(@Param("userId") Long userId);

    List<FranchiseListDTO> findFranchisesByOwnerId(@Param("userId") Long userId);

    FranchiseDetailDTO findHqFranchiseDetailById(@Param("franchiseId") Long franchiseId, @Param("userId") Long userId);

    FranchiseDetailDTO findFranchiseDetailById(@Param("franchiseId") Long franchiseId, @Param("userId") Long userId);

    MyInfoDTO getMyInfo(@Param("userId") Long userId);
}
