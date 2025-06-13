package com.x1.frans.outbound.command.application.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegisterOutboundInfoVO {

    private Long outboundId;
    private LocalDateTime shippedAt;
    private Long deliveryId;
}
