package com.x1.frans.purchase.query.dto;

import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestProductEntity;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequestProductSimpleDto {
    private Long id;
    private Integer no;
    private Integer quantity;
    private String remarks;
    private Long productId;
    private String productCode;
    private String productName;
    private BigDecimal purchasePrice;
    private String purchaseUnit;
    private String productTypeName;
    private String productGroupName;
    private String productAttributeName;

    public PurchaseRequestProductSimpleDto(PurchaseRequestProductEntity entity) {
        this.id = entity.getId();
        this.no = entity.getNo();
        this.quantity = entity.getQuantity();
        this.remarks = entity.getRemarks();
        this.productId = entity.getProductId();
    }

    public static PurchaseRequestProductSimpleDto from(PurchaseRequestProductEntity entity) {
        return new PurchaseRequestProductSimpleDto(entity);
    }
}