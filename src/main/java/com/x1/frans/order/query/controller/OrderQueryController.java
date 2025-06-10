package com.x1.frans.order.query.controller;

import com.x1.frans.order.query.dto.OrderSearchConditionDto;
import com.x1.frans.order.query.dto.OrderSearchPageResponseDto;
import com.x1.frans.order.query.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hq/orders")
@RequiredArgsConstructor
public class OrderQueryController {
    private final OrderQueryService orderQueryService;

    @GetMapping
    public OrderSearchPageResponseDto searchOrdersPaged(
            @ModelAttribute OrderSearchConditionDto condition
    ) {
        return orderQueryService.searchOrders(condition);
    }
}
