package com.x1.frans.approval.common;

public enum ApprovalLineType {
    APPROVER("결재자",true),
    COOPERATOR("협조자",true),
    REFERENCE("참조자",false),
    RECIPIENT("수신자",false);

    private final String description;
    private final boolean isOrdered;

    ApprovalLineType(String description, boolean isOrdered) {
        this.description = description;
        this.isOrdered = isOrdered;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public ApprovalLineStatus getInitialStatus(int index) {
        if (!isOrdered) return null; // 참조자/수신자 → 상태 없음
        return index == 0 ? ApprovalLineStatus.WAITING : ApprovalLineStatus.EXPECTED;
    }

    public String getDescription() {
        return description;
    }
}
