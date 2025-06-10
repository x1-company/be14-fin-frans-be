package com.x1.frans.order.query.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FranchiseOrderQueryMapper {
    Long findFranchiseIdByUserId(Long userId);
}
