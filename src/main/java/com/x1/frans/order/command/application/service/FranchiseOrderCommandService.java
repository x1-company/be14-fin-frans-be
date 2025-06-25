package com.x1.frans.order.command.application.service;

import com.x1.frans.order.command.application.dto.FranchiseOrderCreateRequestDto;
import com.x1.frans.order.command.application.dto.OrderTemplateRequestDTO;

public interface FranchiseOrderCommandService {
    void cancelOrder(Long orderId, Long userId);

    void createOrder(FranchiseOrderCreateRequestDto requestDto, Long userId);

    void createTemplate(Long userId, OrderTemplateRequestDTO orderTemplateRequestDTO);

    void deleteTemplate(Long userId, String templateId);

    void updateTemplate(Long userId, String templateId, OrderTemplateRequestDTO orderTemplateRequestDTO);
}
