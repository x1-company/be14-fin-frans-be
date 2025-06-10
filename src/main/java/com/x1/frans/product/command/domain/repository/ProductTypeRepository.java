package com.x1.frans.product.command.domain.repository;

import com.x1.frans.product.command.domain.aggregate.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Long> {}