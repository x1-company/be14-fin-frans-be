package com.x1.frans.order.command.domain.vo;

public enum OrderStatus {

    WAITING_FOR_RECEIPT("접수 대기"),
    RECEIPT_CANCELED("접수 취소"),
    REJECTED("반려"),
    REVIEWING("검토 중"),
    REVIEW_COMPLETED("검토 완료"),
    APPROVED("결재 완료"),
    READY_FOR_DELIVERY("배송 준비 중"),
    DELIVERING("배송 중"),
    DELIVERED("배송 완료");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

