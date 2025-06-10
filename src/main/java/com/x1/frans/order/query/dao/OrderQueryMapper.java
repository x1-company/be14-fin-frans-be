package com.x1.frans.order.query.dao;

import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSummaryResponseDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderQueryMapper {
    // 목록 조회 (LIMIT, OFFSET 적용)
    List<OrderSummaryResponseDto> searchOrders(OrderSearchConditionDto condition);

    // 전체 개수 조회 (페이징 계산용)
    int countOrders(OrderSearchConditionDto condition);
}
