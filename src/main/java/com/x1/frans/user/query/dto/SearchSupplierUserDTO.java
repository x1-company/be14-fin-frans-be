package com.x1.frans.user.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchSupplierUserDTO extends SearchUserDTO {

    private Integer supplierId;
    private String supplierCode;
    private String supplierName;
    private String supplierCeoName;
    private String supplierCompanyPhone;
    private String supplierAddress;
    private String supplierZipcode;
    private String supplierBusinessNumber;
    private LocalDateTime supplierSignedAt;
}
