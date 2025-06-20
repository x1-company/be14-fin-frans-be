package com.x1.frans.user.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentUserGroupDTO {

    private Long departmentId;
    private String departmentName;
    private List<UserSimpleDTO> users;
}
