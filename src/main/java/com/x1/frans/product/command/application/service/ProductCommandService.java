package com.x1.frans.product.command.application.service;

import com.x1.frans.product.command.application.service.dto.*;
import com.x1.frans.product.command.domain.aggregate.*;
import com.x1.frans.product.command.domain.repository.*;
import com.x1.frans.supplier.command.domain.aggregate.SupplierEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCommandService {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ProductGroupRepository productGroupRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductAttributeRepository productAttributeRepository;

    @Transactional
    public long create(ProductCreateCommand command) {
        if (productRepository.existsByCode(command.getCode())) {
            throw new IllegalArgumentException("이미 존재하는 자재 코드입니다.");
        }
        SupplierEntity supplier = supplierRepository.findById(command.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("공급처 없음"));
        ProductGroupEntity productGroup = productGroupRepository.findById(command.getProductGroupId())
                .orElseThrow(() -> new IllegalArgumentException("분류 없음"));
        ProductTypeEntity productType = productTypeRepository.findById(command.getProductTypeId())
                .orElseThrow(() -> new IllegalArgumentException("구분 없음"));
        ProductAttributeEntity productAttribute = productAttributeRepository.findById(command.getProductAttributeId())
                .orElseThrow(() -> new IllegalArgumentException("속성 없음"));

        ProductEntity product = ProductEntity.create(
                command.getCode(),
                command.getName(),
                command.getPurchasePrice(),
                command.getSalePrice(),
                command.getUnit(),
                command.getSpec(),
                command.isActive(),
                supplier,
                productGroup,
                productType,
                productAttribute
        );
        return productRepository.save(product).getId();
    }

    @Transactional
    public void update(ProductUpdateCommand command) {
        ProductEntity entity = productRepository.findById(command.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 자재 없음"));
        if (!entity.getCode().equals(command.getCode()) &&
                productRepository.existsByCode(command.getCode())) {
            throw new IllegalArgumentException("이미 존재하는 자재 코드입니다.");
        }
        SupplierEntity supplier = supplierRepository.findById(command.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("공급처 없음"));
        ProductGroupEntity productGroup = productGroupRepository.findById(command.getProductGroupId())
                .orElseThrow(() -> new IllegalArgumentException("분류 없음"));
        ProductTypeEntity productType = productTypeRepository.findById(command.getProductTypeId())
                .orElseThrow(() -> new IllegalArgumentException("구분 없음"));
        ProductAttributeEntity productAttribute = productAttributeRepository.findById(command.getProductAttributeId())
                .orElseThrow(() -> new IllegalArgumentException("속성 없음"));

        entity.updateInfo(
                command.getCode(),
                command.getName(),
                command.getPurchasePrice(),
                command.getSalePrice(),
                command.getUnit(),
                command.getSpec(),
                command.isActive(),
                supplier,
                productGroup,
                productType,
                productAttribute
        );
    }

    @Transactional
    public void changeActive(long id, boolean active) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 자재 없음"));
        entity.setActive(active); // active만 변경(비활성화/복구)
    }
}