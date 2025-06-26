package com.x1.frans.approval.common;

public enum ApprovalLineStatus {
    APPROVED("승인"),
    REJECTED("반려"),
    EXPECTED("예정"),
    WAITING("대기");


    private final String description;

    ApprovalLineStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}