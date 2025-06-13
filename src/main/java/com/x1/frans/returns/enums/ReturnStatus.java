package com.x1.frans.returns.enums;

public enum ReturnStatus {
    WAITING_FOR_RECEIPT("접수 대기"),
    REJECTED("반려"),
    REVIEW_COMPLETED("검토 완료"),
    APPROVED("결재 완료"),
    PICKING_UP("반품 수거 중"),
    PICKED_UP("반품 수거 완료"),
    COMPLETED("반품 완료");

    private final String description;

    ReturnStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
