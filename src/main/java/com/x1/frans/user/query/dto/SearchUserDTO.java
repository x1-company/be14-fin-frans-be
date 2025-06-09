package com.x1.frans.user.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchUserDTO {

    private Integer id;
    private String code;
    private String name;
    private String phone;
    private String email;
    private String type;
    private String profileUrl;
}
