package com.x1.frans.product.command.application.service;

import com.x1.frans.product.command.application.service.dto.ProductCreateCommand;
import com.x1.frans.product.command.domain.aggregate.ProductEntity;
import com.x1.frans.product.command.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCommandService {

    private final ProductRepository productRepository;

    public int create(ProductCreateCommand command) {
        // code 중복 검사
        if (productRepository.existsByCode(command.getCode())) {
            throw new IllegalArgumentException("이미 존재하는 자재 코드입니다.");
        }

        ProductEntity product = ProductEntity.create(
                command.getCode(),
                command.getName(),
                command.getPurchasePrice(),
                command.getSalePrice(),
                command.getUnit(),
                command.getSpec(),
                command.isActive(),
                command.getSupplierId(),
                command.getProductGroupId(),
                command.getProductTypeId(),
                command.getProductAttributeId()
        );

        return productRepository.save(product).getId();
    }
}