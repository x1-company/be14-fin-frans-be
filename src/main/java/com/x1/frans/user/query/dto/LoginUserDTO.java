package com.x1.frans.user.query.dto;

import com.x1.frans.user.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginUserDTO {

    private Long id;
    private String code;
    private String password;
    private String name;
    private String phone;
    private String email;
    private UserType type;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private Boolean isLocked;
    private Boolean isTempPassword;
    private String signUrl;
    private String profileUrl;

    // 본사 직원일 경우
    private Long departmentId;
    private Long positionId;
    private Long dutyId;

    // 공급처일 경우
    private Long supplierId;
    private String supplierCode;
    private String supplierName;

    // 가맹점주일 경우
    private Long franchiseId;
    private String franchiseName;
}
