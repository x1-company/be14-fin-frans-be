package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.SupplierDeliveryProductDTO;
import java.util.List;

public interface SupplierDeliveryProductService {

    List<SupplierDeliveryProductDTO> getAllProductsBySupplier(Long supplierId);

    List<SupplierDeliveryProductDTO> getProductsByName(Long supplierId, String name);

    List<SupplierDeliveryProductDTO> getProductsByCode(Long supplierId, String code);

    List<SupplierDeliveryProductDTO> getProductsByNameAndCode(Long supplierId, String name, String code);
}
