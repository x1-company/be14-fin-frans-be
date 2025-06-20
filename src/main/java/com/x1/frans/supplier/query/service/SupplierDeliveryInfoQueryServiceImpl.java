package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.SupplierDeliveryInfoDTO;
import com.x1.frans.supplier.query.repository.SupplierDeliveryInfoMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierDeliveryInfoQueryServiceImpl implements SupplierDeliveryInfoQueryService {

    private final SupplierDeliveryInfoMapper supplierDeliveryInfoMapper;

    @Autowired
    public SupplierDeliveryInfoQueryServiceImpl(SupplierDeliveryInfoMapper supplierDeliveryInfoMapper) {
        this.supplierDeliveryInfoMapper = supplierDeliveryInfoMapper;
    }

    @Override
    public List<SupplierDeliveryInfoDTO> findDeliveryInfos(Long supplierId, Integer year, Integer month, Integer day) {
        // 매퍼 쿼리 호출
        return supplierDeliveryInfoMapper.findDeliveryInfos(supplierId, year, month, day);
    }
}

