package com.x1.frans.approval.common;

import java.util.Arrays;

public enum ApprovalCategoryType {
    ORDER(1L, "주문"),
    RETURN(2L, "반품"),
    PURCHASE_ORDER(3L, "발주");

    private final Long id;
    private final String label;

    ApprovalCategoryType(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    // id로 enum 찾기
    public static ApprovalCategoryType fromId(Long id) {
        for (ApprovalCategoryType type : values()) {
            if (type.id.equals(id)) return type;
        }
        throw new IllegalArgumentException("유효하지 않은 결재 문서 유형 ID " + id);
    }

    // label로 enum 찾기
    public static ApprovalCategoryType fromLabel(String label) {
        for (ApprovalCategoryType type : values()) {
            if (type.label.equals(label)) return type;
        }
        throw new IllegalArgumentException("유효하지 않은 결재 문서 유형: " + label);
    }
}
