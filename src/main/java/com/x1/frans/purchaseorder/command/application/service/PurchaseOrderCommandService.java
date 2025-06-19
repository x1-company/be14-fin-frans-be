package com.x1.frans.purchaseorder.command.application.service;

import com.x1.frans.purchaseorder.command.application.dto.PurchaseOrderSaveRequestDto;
import com.x1.frans.purchaseorder.command.application.dto.PurchaseOrderUpdateRequestDto;

public interface PurchaseOrderCommandService {
    Long saveDraft(PurchaseOrderSaveRequestDto dto, Long userId);

    void updateDraft(Long purchaseOrderId, PurchaseOrderUpdateRequestDto dto, Long userId);
}