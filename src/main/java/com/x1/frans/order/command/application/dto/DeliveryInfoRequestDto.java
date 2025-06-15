package com.x1.frans.order.command.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeliveryInfoRequestDto {

    @NotBlank(message = "배송 업체는 필수입니다.")
    private String deliveryCompany;

    @NotBlank(message = "운송장 번호는 필수입니다.")
    private String trackingNumber;

    @NotBlank(message = "배송 기사명은 필수입니다.")
    private String name;

    @NotBlank(message = "배송 기사 연락처는 필수입니다.")
    private String phone;
}
