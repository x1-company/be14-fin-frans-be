package com.x1.frans.purchaseorder.query.service;

import com.x1.frans.purchaseorder.query.dto.PurchaseOrderRequestPendingListDto;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderDraftListDto;
import com.x1.frans.purchaseorder.query.repository.PurchaseOrderQueryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderQueryService {
    private final PurchaseOrderQueryMapper mapper;

    public PurchaseOrderQueryService(PurchaseOrderQueryMapper mapper) {
        this.mapper = mapper;
    }

    public List<PurchaseOrderDraftListDto> getDraftList() {
        List<PurchaseOrderDraftListDto> result = mapper.selectDraftOrders();
        return result;
    }


    public List<PurchaseOrderRequestPendingListDto> getRequestPending(Long userId) {
        return mapper.getRequestPending(userId);
    }
}
