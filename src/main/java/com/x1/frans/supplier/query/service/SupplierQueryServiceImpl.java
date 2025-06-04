package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.repository.SupplierQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierQueryServiceImpl implements SupplierQueryService {

    private final SupplierQueryMapper supplierQueryMapper;

    @Autowired
    public SupplierQueryServiceImpl(SupplierQueryMapper supplierQueryMapper) {
        this.supplierQueryMapper = supplierQueryMapper;
    }

    @Override
    public String findLatestCodeByCodePrefix(String codePrefix) {
        return supplierQueryMapper.findLatestCodeByCodePrefix(codePrefix);
    }
}
