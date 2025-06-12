package com.x1.frans.user.command.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FranchiseUserRequestVO extends CreateUserRequestVO {

    private String franchiseName;
    private String address;
    private String addressDetail;
    private String zipcode;
    private String businessNumber;
    private String franchisePhone;
    private LocalDate signedAt;
    private Long departmentId;
    private Long managerId;
}
