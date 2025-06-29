package com.x1.frans.purchaseorder.command.application.service;

import com.x1.frans.purchaseorder.command.application.dto.PurchaseOrderSaveRequestDto;
import com.x1.frans.purchaseorder.command.application.dto.PurchaseOrderStatusUpdateRequestDto;
import com.x1.frans.purchaseorder.command.application.dto.PurchaseOrderUpdateRequestDto;

public interface PurchaseOrderCommandService {
    Long saveDraft(PurchaseOrderSaveRequestDto dto, Long userId);

    void updateDraft(Long purchaseOrderId, PurchaseOrderUpdateRequestDto dto, Long userId);

    void delete(Long purchaseOrderId, Long userId);

    void requestOrder(Long purchaseOrderId, PurchaseOrderUpdateRequestDto dto, Long userId);

    Long saveAndRequest(PurchaseOrderSaveRequestDto dto, Long userId);

    void cancel(Long purchaseOrderId, Long userId);

    void updateStatus(Long purchaseOrderId, PurchaseOrderStatusUpdateRequestDto dto, Long userId);

    void updateRegisteredOrder(Long purchaseOrderId, PurchaseOrderUpdateRequestDto dto, Long userId);
}