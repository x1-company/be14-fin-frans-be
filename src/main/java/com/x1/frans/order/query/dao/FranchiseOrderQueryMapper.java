package com.x1.frans.order.query.dao;

import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSummaryResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FranchiseOrderQueryMapper {
    // 목록 조회 (LIMIT, OFFSET 적용)
    List<OrderSummaryResponseDto> searchOrders(OrderSearchConditionDto condition);

    // 전체 개수 조회 (페이징 계산용)
    int countOrders(OrderSearchConditionDto condition);
}
