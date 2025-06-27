package com.x1.frans.purchaseorder.query.dto;

import com.x1.frans.supplier.query.dto.SupplierDetailDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderDetailDto {
    private Long id;
    private String code;
    private String title;
    private String status;
    private LocalDate requestedDeliveryDate;
    private String userName;
    private String userEmail;
    private SupplierDetailDTO supplier;
    private BigDecimal totalAmount;
    private List<PurchaseOrderProductDetailDto> products;
}