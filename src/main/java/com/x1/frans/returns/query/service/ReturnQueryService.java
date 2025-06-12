package com.x1.frans.returns.query.service;

import com.x1.frans.returns.query.dto.ShippedOrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReturnQueryService {

    List<ShippedOrderDTO> findDeliveredOrdersWithinLastWeek(Long userId, Long FranchiseId);

}
