package com.x1.frans.returns.query.dto;

import com.x1.frans.returns.command.domain.aggregate.ReturnFileEntity;
import com.x1.frans.returns.enums.ReturnStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "본사 반품 상세 정보 조회")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnDetailDTO {

    // 가맹점 정보
    @Schema(description = "가맹점 명")
    private String franchiseName;

    @Schema(description = "가맹점 코드")
    private String franchiseCode;

    @Schema(description = "사업자 번호")
    private String businessNumber;

    @Schema(description = "대표자명")
    private String ceoName;

    @Schema(description = "가맹점 계약날짜")
    private LocalDate franchiseSignedAt;

    @Schema(description = "가맹점 주소")
    private String franchiseAddress;

    @Schema(description = "가맹점 상세 주소")
    private String franchiseAddressDetail;

    @Schema(description = "우편번호")
    private String zipCode;

    @Schema(description = "가맹점 번호")
    private String franchisePhone;

    // 반품 정보
    @Schema(description = "반품 코드")
    private String returnCode;

    @Schema(description = "반품 요청일")
    private LocalDateTime createdAt;

    @Schema(description = "총 반품 금액")
    private BigDecimal totalAmount;

    @Schema(description = "반품 상태")
    private ReturnStatus status;

    @Schema(description = "반품 사유")
    private String description;

    @Schema(description = "반려 사유")
    private String rejectedReason;

    @Schema(description = "반품 첨부 파일")
    List<ReturnFileEntity> files;

    // 자재 정보
    @Schema(description = "반품 자재 정보")
    private List<ReturnProductDTO> returnProducts;

    // 배송 정보
    @Schema(description = "택배사 이름")
    private String deliveryCompany;

    @Schema(description = "배송 기사 이름")
    private String driverName;

    @Schema(description = "배송기사 연락처")
    private String driverPhone;

    @Schema(description = "송장번호")
    private String trackingNumber;

    @Schema(description = "배송 완료일")
    private LocalDate deliveredAt;
}
