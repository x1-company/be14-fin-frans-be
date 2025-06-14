package com.x1.frans.user.enums;

import lombok.Getter;

@Getter
public enum DutyType {

    STAFF(1, "팀원"),
    LEAD(2, "팀장"),
    HEAD(3, "부장");

    private final long id;
    private final String label;

    DutyType(long id, String label) {
        this.id = id;
        this.label = label;
    }
}
