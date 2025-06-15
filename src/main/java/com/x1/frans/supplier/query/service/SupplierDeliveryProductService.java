package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.SupplierDeliveryProductDTO;
import java.util.List;

public interface SupplierDeliveryProductService {

    List<SupplierDeliveryProductDTO> getAllProductsBySupplier(Long supplierId);

}
