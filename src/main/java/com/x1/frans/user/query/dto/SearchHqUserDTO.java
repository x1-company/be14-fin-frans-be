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

    private Long departmentId;
    private String departmentName;
    private Long dutyId;
    private String dutyName;
    private Long positionId;
    private String positionName;
}
