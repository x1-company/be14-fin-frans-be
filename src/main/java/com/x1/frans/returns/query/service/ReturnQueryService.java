package com.x1.frans.returns.query.service;

import com.x1.frans.returns.query.dto.ProductOrderDTO;
import com.x1.frans.returns.query.dto.ShippedOrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReturnQueryService {

    List<ShippedOrderDTO> findDeliveredOrdersWithinLastWeek(Long userId, Long franchiseId);

    List<ProductOrderDTO> findProductListByOrderId(Long userId, Long franchiseId, Long orderId);

}
