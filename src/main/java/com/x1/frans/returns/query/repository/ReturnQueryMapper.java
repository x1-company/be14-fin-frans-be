package com.x1.frans.returns.query.repository;

import com.x1.frans.returns.query.dto.ShippedOrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReturnQueryMapper {
    List<ShippedOrderDTO> findDeliveredOrdersWithinLastWeek(@Param("userId") Long userId, @Param("franchiseId") Long franchiseId);
}
