package com.x1.frans.statistics.query.view.repository;

import com.x1.frans.statistics.query.view.dto.FranchiseProductOrderQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FranchiseProductOrderMapper {

    List<FranchiseProductOrderQueryDTO> getStats(@Param("franchiseId") long franchiseId,
                                                 @Param("year") int year, @Param("month") int month);

    boolean existsFranchiseByManagerId(Long managerId);

    List<FranchiseProductOrderQueryDTO> selectStatsByManager(@Param("managerId") Long managerId,
                                                             @Param("year") Integer year,
                                                             @Param("month") Integer month);

    List<FranchiseProductOrderQueryDTO> selectStatsByManagerByFranchiseId(
            @Param("userId") Long userId,
            @Param("franchiseId") Long franchiseId
    );
}
