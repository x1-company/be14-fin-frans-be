package com.x1.frans.supplier.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupplierListDTO {

    @Schema(description = "공급처 이름")
    String name;
    @Schema(description = "대표자 이름")
    String ceoName;
    @Schema(description = "회사 대표 전화번호")
    String companyPhone;
}
