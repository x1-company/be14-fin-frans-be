package com.x1.frans.purchase.query.dto;

import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestProductEntity;
import lombok.*;

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