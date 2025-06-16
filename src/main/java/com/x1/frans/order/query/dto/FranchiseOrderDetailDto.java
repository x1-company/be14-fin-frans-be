package com.x1.frans.order.query.dto;

import com.x1.frans.product.query.dto.ProductDetailDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FranchiseOrderDetailDto {
    // 가맹점 정보
    private String franchiseName;
    private String franchiseCode;
    private String businessNumber;
    private String ceoName;
    private LocalDate franchiseSignedAt;

    private String franchiseAddress;
    private String zipCode;
    private String franchisePhone;

    // 주문 정보
    private String status;
    private String code;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private int totalQuantity;
    private String rejectedReason;

    // 자재 정보
    private List<ProductDetailDTO> products;

    // 배송 정보
    private String deliveryCompany;
    private String driverName;
    private String driverPhone;
    private String trackingNumber;
    private LocalDate deliveredAt;
}
