package com.x1.frans.supplier.command.application.service;

import com.x1.frans.supplier.command.application.dto.DeliveryInfoCreateRequestDTO;
import com.x1.frans.supplier.command.application.dto.DeliveryInfoModifyDTO;
import jakarta.validation.Valid;

public interface SupplierDeliveryInfoCommandService {

    Long createDeliveryInfo(Long supplierId, String supplierCode, DeliveryInfoCreateRequestDTO requestDTO);

    void modifyDeliveryInfo(@Valid DeliveryInfoModifyDTO modifyDTO, Long supplierId, String supplierCode);

}
