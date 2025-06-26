package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.SupplierDetailDTO;
import com.x1.frans.supplier.query.dto.SupplierListDTO;
import com.x1.frans.supplier.query.repository.SupplierHqQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierHqQueryServiceImpl implements SupplierHqQueryService {

    private final SupplierHqQueryMapper supplierQueryMapper;

    @Autowired
    public SupplierHqQueryServiceImpl(SupplierHqQueryMapper supplierQueryMapper) {
        this.supplierQueryMapper = supplierQueryMapper;
    }

    @Override
    public String findLatestCodeByCodePrefix(String codePrefix) {
        return supplierQueryMapper.findLatestCodeByCodePrefix(codePrefix);
    }

    @Override
    public List<SupplierListDTO> getAllSuppliers() {
        return supplierQueryMapper.findSupplierList();
    }

    @Override
    public SupplierDetailDTO getSupplierDetail(long supplierId) {
        return supplierQueryMapper.getSupplierDetail(supplierId);
    }
}
