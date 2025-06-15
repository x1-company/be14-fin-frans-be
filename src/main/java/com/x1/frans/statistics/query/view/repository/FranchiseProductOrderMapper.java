package com.x1.frans.statistics.query.view.repository;

import com.x1.frans.statistics.query.view.dto.FranchiseProductOrderQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FranchiseProductOrderMapper {

    List<FranchiseProductOrderQueryDTO> getStats(@Param("franchiseId") long franchiseId,
                                                 @Param("year") int year, @Param("month") int month);
}
