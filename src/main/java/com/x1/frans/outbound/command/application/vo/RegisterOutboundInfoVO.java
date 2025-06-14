package com.x1.frans.outbound.command.application.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegisterOutboundInfoVO {

    private Long outboundId;
    private LocalDateTime shippedAt;
    private Long deliveryId;
    private List<Long> orderIds;
}
