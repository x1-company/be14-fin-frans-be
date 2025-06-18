package com.x1.frans.purchaseorder.command.application.service;

import com.x1.frans.purchaseorder.command.domain.aggregate.PurchaseOrderEntity;

public interface PurchaseOrderCommandService {
    PurchaseOrderEntity saveDraft(PurchaseOrderEntity entity, Long userId);
}