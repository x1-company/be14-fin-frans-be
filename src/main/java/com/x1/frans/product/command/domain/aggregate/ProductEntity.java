package com.x1.frans.product.command.domain.aggregate;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private BigDecimal purchasePrice;

    @Column(nullable = false)
    private BigDecimal salePrice;

    @Column(nullable = false, length = 50)
    private String unit;

    @Column(nullable = false, length = 50)
    private String spec;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private int supplierId;
    private int productGroupId;
    private int productTypeId;
    private int productAttributeId;

    public static ProductEntity create(
            String code,
            String name,
            BigDecimal purchasePrice,
            BigDecimal salePrice,
            String unit,
            String spec,
            boolean isActive,
            int supplierId,
            int productGroupId,
            int productTypeId,
            int productAttributeId
    ) {
        return ProductEntity.builder()
                .code(code)
                .name(name)
                .purchasePrice(purchasePrice)
                .salePrice(salePrice)
                .unit(unit)
                .spec(spec)
                .isActive(isActive)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .supplierId(supplierId)
                .productGroupId(productGroupId)
                .productTypeId(productTypeId)
                .productAttributeId(productAttributeId)
                .build();
    }
}
