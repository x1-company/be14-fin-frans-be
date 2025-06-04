package com.x1.frans.user.command.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SupplierUserRequestVO extends CreateUserRequestVO {

    private String supplierName;
    private String ceoName;
    private String companyPhone;
    private String address;
    private String zipCode;
    private String businessNumber;
    private Integer hqUserId;
}
