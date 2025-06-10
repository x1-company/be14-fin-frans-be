package com.x1.frans.supplier.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
@Schema(description = "공급처 상세조회 DTO")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplierDetailDTO {

    @Schema(description = "공급처 id")
    private Long id;

    @Schema(description = "공급처 코드")
    private String code;

    @Schema(description = "공급처 이름")
    private String name;

    @Schema(description = "대표자 이름")
    private String ceoName;

    @Schema(description = "대표자 번호")
    private String companyPhone;

    @Schema(description = "주소")
    private String address;

    @Schema(description = "우편번호")
    private String zipcode;

    @Schema(description = "사업자등록번호")
    private String businessNumber;

    @Schema(description = "계약일자")
    private LocalDateTime signedAt;

    @Schema(description = "거래여부")
    private Boolean isActive;

    @Schema(description = "생성일")
    private LocalDateTime createdAt;

    @Schema(description = "수정일")
    private LocalDateTime updatedAt;

}
