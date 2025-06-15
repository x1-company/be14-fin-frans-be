package com.x1.frans.supplier.command.application.service;

import com.x1.frans.supplier.command.application.dto.DeliveryInfoCreateRequestDTO;

public interface SupplierDeliveryInfoCommandService {

    Long createDeliveryInfo(Long supplierId, String supplierCode, DeliveryInfoCreateRequestDTO requestDTO);

}
