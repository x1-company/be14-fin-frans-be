package com.x1.frans.order.query.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FranchiseOrderQueryMapper {
    List<Long> findFranchiseIdsByUserId(@Param("userId") Long userId);
}
