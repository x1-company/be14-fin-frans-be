package com.x1.frans.purchase.command.application.service;

import com.x1.frans.purchase.command.application.service.dto.PurchaseRequestCreateCommand;
import com.x1.frans.purchase.command.application.service.dto.PurchaseRequestUpdateCommand;

public interface PurchaseRequestCommandService {
    Long create(PurchaseRequestCreateCommand command, Long userId);

    void update(Long purchaseRequestId, PurchaseRequestUpdateCommand command, Long userId);

    void delete(Long purchaseRequestId, Long userId);
}
