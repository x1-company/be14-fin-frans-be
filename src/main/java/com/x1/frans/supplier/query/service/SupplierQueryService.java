package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.SupplierDetailDTO;

public interface SupplierQueryService {
    String findLatestCodeByCodePrefix(String codePrefix);

    SupplierDetailDTO getSupplierDetail(Long supplierId);
}
