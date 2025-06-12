package com.x1.frans.user.command.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SupplierUserRequestVO extends CreateUserRequestVO {

    private String supplierName;
    private String ceoName;
    private String companyPhone;
    private String address;
    private String zipcode;
    private String businessNumber;
    private LocalDateTime signedAt;
    private Long managerId;
}
