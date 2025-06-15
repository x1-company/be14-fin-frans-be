package com.x1.frans.returns.query.repository;

import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSummaryResponseDto;
import com.x1.frans.returns.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReturnQueryMapper {

    List<ShippedOrderDTO> findDeliveredOrdersWithinLastWeek(@Param("userId") Long userId,
                                                            @Param("franchiseId") Long franchiseId);

    List<ProductOrderDTO> findProductListByOrderId(@Param("userId") Long userId,
                                                   @Param("franchiseId") Long franchiseId,
                                                   @Param("orderId") Long orderId);

    // 목록 조회 (LIMIT, OFFSET 적용)
    List<ReturnListDTO> searchReturns(ReturnSearchConditionDTO condition);

    // 전체 개수 조회 (페이징 계산용)
    int countReturns(ReturnSearchConditionDTO condition);

    // 가맹점주가 소유하는 가맹점 목록 조회
    List<Long>  findFranchiseIdsByUserId(Long userId);
}
