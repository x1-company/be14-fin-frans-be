package com.x1.frans.purchaseorder.enums;

public enum PurchaseOrderStatus {
    DRAFT("임시저장"),
    APPROVED("승인 완료"),
    REJECTED("반려"),
    REQUEST_PENDING("발주 대기"),
    CANCELED("발주 취소");

    private final String label;

    PurchaseOrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}