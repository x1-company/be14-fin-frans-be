package com.x1.frans.supplier.query.dto;

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
}
