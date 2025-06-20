package com.x1.frans.purchase.query.dto;

import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequestDetailDto {
    private Long id;
    private String code;
    private String title;
    private String status;
    private LocalDate requestedDeliveryDate;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PurchaseRequestProductSimpleDto> products;

    public PurchaseRequestDetailDto(PurchaseRequestEntity entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.title = entity.getTitle();
        this.status = entity.getStatus().getLabel();
        this.requestedDeliveryDate = entity.getRequestedDeliveryDate();
        this.totalAmount = entity.getTotalAmount();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.products = entity.getPurchaseRequestProducts().stream()
                .map(PurchaseRequestProductSimpleDto::from)
                .collect(Collectors.toList());
    }

    public static PurchaseRequestDetailDto from(PurchaseRequestEntity entity) {
        return new PurchaseRequestDetailDto(entity);
    }
}