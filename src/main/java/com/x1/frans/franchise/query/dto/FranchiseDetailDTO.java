package com.x1.frans.franchise.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseDetailDTO {

    private String code;
    private String name;
    private String address;
    private String addressDetail;
    private String zipcode;
    private String businessNumber;
    private String phone;
    private LocalDate signedAt;
    private Boolean isActive;
}
