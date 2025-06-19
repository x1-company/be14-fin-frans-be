package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.SupplierDeliveryProductDTO;
import com.x1.frans.supplier.query.repository.SupplierDeliveryProductMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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

    @Override
    public List<SupplierDeliveryProductDTO> getProductsByName(Long supplierId, String name) {
        return supplierDeliveryProductMapper.findProductByName(supplierId, name);
    }

    @Override
    public List<SupplierDeliveryProductDTO> getProductsByCode(Long supplierId, String code) {
        return supplierDeliveryProductMapper.findProductByCode(supplierId, code);
    }

    @Override
    public List<SupplierDeliveryProductDTO> getProductsByNameAndCode(Long supplierId, String name, String code) {
        return supplierDeliveryProductMapper.findProductByNameAndCode(supplierId, name, code);
    }
}
