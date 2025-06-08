package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.SupplierListDTO;

import java.util.List;

public interface SupplierQueryService {
    String findLatestCodeByCodePrefix(String codePrefix);

    List<SupplierListDTO> getAllSuppliers();
}
