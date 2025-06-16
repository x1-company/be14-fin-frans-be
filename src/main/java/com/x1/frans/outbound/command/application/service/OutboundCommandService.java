package com.x1.frans.outbound.command.application.service;

import com.x1.frans.outbound.command.application.vo.RegisterOutboundInfoVO;

import java.util.List;

public interface OutboundCommandService {

    void generateOutbound(List<Long> orderIds);

    void registerOutboundInfo(RegisterOutboundInfoVO registerOutboundInfoVO, Long userId);
}
