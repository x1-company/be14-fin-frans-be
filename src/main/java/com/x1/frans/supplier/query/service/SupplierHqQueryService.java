package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.SupplierListDTO;

import java.util.List;

import com.x1.frans.supplier.query.dto.SupplierDetailDTO;

public interface SupplierHqQueryService {
    String findLatestCodeByCodePrefix(String codePrefix);

    List<SupplierListDTO> getAllSuppliers();

    SupplierDetailDTO getSupplierDetail(long supplierId);
}
