package com.x1.frans.outbound.command.application.controller;

import com.x1.frans.outbound.command.application.service.OutboundCommandService;
import com.x1.frans.outbound.command.application.vo.RegisterOutboundInfoVO;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/hq/outbound")
@Tag(name = "📦 출고", description = "본사 직원이 사용하는 출고 관련 기능들")
public class OutboundCommandController {
    
    private final OutboundCommandService outboundCommandService;
    
    @Autowired
    public OutboundCommandController(OutboundCommandService outboundCommandService) {
        this.outboundCommandService = outboundCommandService;
    }
    
    @PatchMapping
    @Operation(
            summary = "출고 정보 등록",
            description = "물류팀 직원이 출고 정보 등록"
    )
    public ResponseEntity<Void> registerOutboundInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody RegisterOutboundInfoVO registerOutboundInfoVO) {

        outboundCommandService.registerOutboundInfo(registerOutboundInfoVO, customUserDetails.getUserId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
