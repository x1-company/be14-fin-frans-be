package com.x1.frans.user.enums;

import lombok.Getter;

@Getter
public enum PositionType {

    STAFF(1, "사원"),
    AM(2, "대리"),
    MGR(3, "과장"),
    SM(4, "차장"),
    GM(5, "부장"),
    DIR(6, "이사"),
    ED(7, "전무"),
    CEO(8, "사장");

    private final long id;
    private final String label;

    PositionType(long id, String label) {
        this.id = id;
        this.label = label;
    }
}
