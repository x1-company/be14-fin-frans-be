package com.x1.frans.franchise.command.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateFranchiseRequestVO {

    private String name;
    private String address;
    private String addressDetail;
    private String zipcode;
    private String businessNumber;
    private String phone;
    private LocalDate signedAt;
    private Boolean isActive;

}
