package com.x1.frans.product.command.domain.aggregate;

import com.x1.frans.supplier.command.domain.aggregate.SupplierEntity;
import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

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
    private String purchaseUnit;

    @Column(nullable = false, length = 50)
    private String spec;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

    @ManyToOne
    @JoinColumn(name = "product_group_id")
    private ProductGroupEntity productGroup;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductTypeEntity productType;

    @ManyToOne
    @JoinColumn(name = "product_attribute_id")
    private ProductAttributeEntity productAttribute;

    /** 자제 등록 */
    public static ProductEntity create(
            String code,
            String name,
            BigDecimal purchasePrice,
            BigDecimal salePrice,
            String unit,
            String purchaseUnit,
            String spec,
            boolean active,
            SupplierEntity supplier,
            ProductGroupEntity productGroup,
            ProductTypeEntity productType,
            ProductAttributeEntity productAttribute
    ) {
        return ProductEntity.builder()
                .code(code)
                .name(name)
                .purchasePrice(purchasePrice)
                .salePrice(salePrice)
                .unit(unit)
                .purchaseUnit(purchaseUnit)
                .spec(spec)
                .active(active)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .supplier(supplier)
                .productGroup(productGroup)
                .productType(productType)
                .productAttribute(productAttribute)
                .build();
    }

    /** 자제 수정 */
    public void updateInfo(
            String code,
            String name,
            BigDecimal purchasePrice,
            BigDecimal salePrice,
            String unit,
            String purchaseUnit,
            String spec,
            boolean active,
            SupplierEntity supplier,
            ProductGroupEntity productGroup,
            ProductTypeEntity productType,
            ProductAttributeEntity productAttribute
    ) {
        this.code = code;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.unit = unit;
        this.purchaseUnit = purchaseUnit;
        this.spec = spec;
        this.active = active;
        this.supplier = supplier;
        this.productGroup = productGroup;
        this.productType = productType;
        this.productAttribute = productAttribute;
        this.updatedAt = LocalDateTime.now();
    }

    /** 미사용/사용 전용 Setter */
    public void setActive(boolean active) {
        this.active = active;
        this.updatedAt = LocalDateTime.now();
    }
}