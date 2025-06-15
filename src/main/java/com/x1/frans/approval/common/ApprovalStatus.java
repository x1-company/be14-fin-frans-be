package com.x1.frans.approval.common;

public enum ApprovalStatus {
    APPROVED("결재 완료"),
    REJECTED("결재 반려"),
    IN_PROGRESS("결재 중"),
    DRAFT("임시 저장");

    private final String description;

    ApprovalStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
