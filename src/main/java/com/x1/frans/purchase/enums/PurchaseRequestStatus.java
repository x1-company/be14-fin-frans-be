package com.x1.frans.purchase.enums;

public enum PurchaseRequestStatus {
    DRAFT("임시저장"),
    REQUEST_CANCEL("요청 취소"),
    REQUEST_PENDING("요청 대기"),
    REVIEWING("검토중"),
    APPROVED("승인"),
    REJECTED("반려");

    private final String label;

    PurchaseRequestStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}