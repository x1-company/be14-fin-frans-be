package com.x1.frans.order.command.application.service;

import com.x1.frans.order.command.application.dto.FranchiseOrderCreateRequestDto;

public interface FranchiseOrderCommandService {
    void cancelOrder(Long orderId, Long userId);

    void createOrder(FranchiseOrderCreateRequestDto requestDto, Long userId);
}
