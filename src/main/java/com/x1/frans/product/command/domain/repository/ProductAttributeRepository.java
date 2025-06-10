package com.x1.frans.product.command.domain.repository;

import com.x1.frans.product.command.domain.aggregate.ProductAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepository extends JpaRepository<ProductAttributeEntity, Long> {}