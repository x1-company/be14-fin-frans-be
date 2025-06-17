package com.x1.frans.returns.query.service;

import com.x1.frans.returns.query.dto.*;

import java.util.List;

public interface ReturnQueryService {

    List<ShippedOrderDTO> findDeliveredOrdersWithinLastWeek(Long userId, Long franchiseId);

    List<ProductOrderDTO> findProductListByOrderId(Long userId, Long franchiseId, Long orderId);

    ReturnSearchPageDTO findAllReturns(Long userId, ReturnSearchConditionDTO condition);

    HqReturnDetailDTO findHqReturnDetailById(Long userId, Long returnId);

    ReturnDetailDTO findReturnDetailById(Long userId, Long returnId);
}
