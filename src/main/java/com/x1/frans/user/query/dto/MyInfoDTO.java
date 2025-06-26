package com.x1.frans.user.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MyInfoDTO {

    private Long id;
    private String code;
    private String name;
    private String phone;
    private String email;
    private String profileUrl;

    private Long departmentId;
    private String departmentName;

    private Long dutyId;
    private String dutyName;

    private Long positionId;
    private String positionName;
}
