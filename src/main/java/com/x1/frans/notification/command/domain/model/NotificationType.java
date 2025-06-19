package com.x1.frans.notification.command.domain.model;

public enum NotificationType {
    ABNORMAL_ORDER("비정상적인 주문이 감지되었습니다."),
    LOW_STOCK("재고가 부족합니다."),
    APPROVAL_REQUEST("결재 요청이 도착했습니다."),
    APPROVAL_RESPONSE("결재 응답이 도착했습니다."),
    DELIVERY_INFO_RESPONSE("납품 정보가 업데이트되었습니다."),
    ABNORMAL_PRODUCT("품질검사에서 이상 자재가 감지되었습니다."),
    ORDER_RESPONSE("주문 상태가 변경되었습니다."),
    RETURN_REQUEST("반품 요청이 도착했습니다."),
    RETURN_RESPONSE("반품 상태가 변경 되었습니다.");

    private final String message;

    NotificationType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

