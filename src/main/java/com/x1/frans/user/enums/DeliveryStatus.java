package com.x1.frans.user.enums;

public enum DeliveryStatus {

    UNREGISTERED("미등록"),
    DELIVERING("배송 중"),
    DELIVERED("배송 완료"),
    PICKING_UP("반품 수거 중"),
    PICKED_UP("반품 수거 완료");

    private final String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}