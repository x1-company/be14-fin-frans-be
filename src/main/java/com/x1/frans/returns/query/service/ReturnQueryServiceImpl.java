package com.x1.frans.returns.query.service;

import com.x1.frans.returns.query.dto.ProductOrderDTO;
import com.x1.frans.returns.query.dto.ShippedOrderDTO;
import com.x1.frans.returns.query.repository.ReturnQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnQueryServiceImpl implements ReturnQueryService {

    private final ReturnQueryMapper returnQueryMapper;

    @Autowired
    public ReturnQueryServiceImpl(ReturnQueryMapper returnQueryMapper) {
        this.returnQueryMapper = returnQueryMapper;
    }

    @Override
    public List<ShippedOrderDTO> findDeliveredOrdersWithinLastWeek(Long userId, Long franchiseId) {
        return returnQueryMapper.findDeliveredOrdersWithinLastWeek(userId, franchiseId);
    }

    @Override
    public List<ProductOrderDTO> findProductListByOrderId(Long userId, Long franchiseId, Long orderId) {
        return returnQueryMapper.findProductListByOrderId(userId, franchiseId, orderId);
    }
}
