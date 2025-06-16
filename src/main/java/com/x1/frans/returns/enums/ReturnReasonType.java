package com.x1.frans.returns.enums;

public enum ReturnReasonType {
    WRONG_DELIVERY("오배송"),
    OVER_QUANTITY("수량 이상"),
    DAMAGED("파손"),
    BAD_QUALITY("품질 불량"),
    EXPIRING_SOON("유통기한 임박"),
    ETC("기타");

    private final String description;

    ReturnReasonType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
