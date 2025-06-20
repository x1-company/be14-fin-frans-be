package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.SupplierDeliveryInfoDTO;
import java.util.List;

public interface SupplierDeliveryInfoQueryService {

    List<SupplierDeliveryInfoDTO> findDeliveryInfos(Long supplierId, Integer year, Integer month, Integer day);

}
