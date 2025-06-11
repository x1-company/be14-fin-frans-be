package com.x1.frans.user.command.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FranchiseUserRequestVO extends CreateUserRequestVO {

    private String franchiseName;
    private String address;
    private String addressDetail;
    private String zipcode;
    private String businessNumber;
    private String franchisePhone;
    private LocalDate signedAt;
    private Integer departmentId;
    private Long managerId;
}
