package com.x1.frans.supplier.command.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "공급처 정보 수정 vo")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplierUpdateRequestVO {

    @Schema(description = "공급처명")
    private String name;

    @Schema(description = "대표자명")
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

    @Schema(description = "공급처 담당자명")
    private String supplierName;

    @Schema(description = "공급처 담당자 이메일")
    private String supplierEmail;

    @Schema(description = "공급처 담당자 전화번호")
    private String supplierPhone;


}
