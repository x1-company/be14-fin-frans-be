package com.x1.frans.user.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchHqUserDTO extends SearchUserDTO {

    private Integer departmentId;
    private String departmentName;
    private Integer dutyId;
    private String dutyName;
    private Integer positionId;
    private String positionName;
}
