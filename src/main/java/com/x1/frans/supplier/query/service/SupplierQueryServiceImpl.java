package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.MyInfoDTO;
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
    public MyInfoDTO getMyInfo(Long userId) {
        return supplierQueryMapper.getMyInfo(userId);
    }
}
