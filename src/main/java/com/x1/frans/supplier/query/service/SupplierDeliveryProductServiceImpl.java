package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.SupplierDeliveryProductDTO;
import com.x1.frans.supplier.query.repository.SupplierDeliveryProductMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierDeliveryProductServiceImpl implements SupplierDeliveryProductService {

    private final SupplierDeliveryProductMapper supplierDeliveryProductMapper;

    @Autowired
    public SupplierDeliveryProductServiceImpl(SupplierDeliveryProductMapper supplierDeliveryProductMapper) {
        this.supplierDeliveryProductMapper = supplierDeliveryProductMapper;
    }

    @Override
    public List<SupplierDeliveryProductDTO> getAllProductsBySupplier(Long supplierId) {
        return supplierDeliveryProductMapper.findAllBySupplierId(supplierId);
    }

}
