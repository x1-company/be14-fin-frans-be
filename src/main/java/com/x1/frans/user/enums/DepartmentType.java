package com.x1.frans.user.enums;

import lombok.Getter;

@Getter
public enum DepartmentType {

    PUR(1, "구매팀"),
    SAL(2, "영업팀"),
    LOG(3, "물류팀"),
    SAL_1(4, "영업1팀"),
    SAL_2(5, "영업1팀"),
    SAL_3(6, "영업1팀"),
    LOG_1(7, "물류1팀"),
    LOG_2(8, "물류2팀"),
    LOG_3(9, "물류3팀"),
    HRM(10, "인사팀");

    private final long id;
    private final String label;

    DepartmentType(long id, String label) {
        this.id = id;
        this.label = label;
    }
}
