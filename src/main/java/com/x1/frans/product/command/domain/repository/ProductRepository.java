package com.x1.frans.product.command.domain.repository;

import com.x1.frans.product.command.domain.aggregate.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByCode(String code);
}