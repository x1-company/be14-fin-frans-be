package com.x1.frans.returns.enums;

public enum ProductReturnStatus {
    WAITING("대기 중"),
    APPROVED("승인"),
    REJECTED("반려");

    private final String description;
    
    ProductReturnStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
