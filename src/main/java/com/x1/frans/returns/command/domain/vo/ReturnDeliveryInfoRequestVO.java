package com.x1.frans.returns.command.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "반품 회수 배송 정보")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReturnDeliveryInfoRequestVO {

    @Schema(name = "배송 업체")
    private String deliveryCompany;

    @Schema(name = "운송장 번호")
    private String trackingNumber;

    @Schema(name = "배송 기사 이름")
    private String name;

    @Schema(name = "기사 연락처")
    private String phone;

}
