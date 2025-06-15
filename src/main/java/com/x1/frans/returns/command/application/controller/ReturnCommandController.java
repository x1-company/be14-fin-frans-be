package com.x1.frans.returns.command.application.controller;

import com.x1.frans.returns.command.application.service.ReturnCommandService;
import com.x1.frans.returns.command.domain.vo.ReturnCreateRequestVO;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "🔄 반품", description = "반품 관련 API")
@RestController
@RequestMapping("/api/franchise")
public class ReturnCommandController {

    private final ReturnCommandService returnCommandService;

    @Autowired
    public ReturnCommandController(ReturnCommandService returnCommandService) {
        this.returnCommandService = returnCommandService;
    }

    @Operation(summary = "반품 정보 등록", description ="주문서 안의 자재 목록 중 선택하여 반품할 자재 및 반품 사유와 첨부파일을 등록할 수 있다.")
    @PostMapping("{franchiseId}/return/regist")
    public ResponseEntity registReturn(@PathVariable("franchiseId") Long franchiseId,
                                       @RequestBody ReturnCreateRequestVO vo,
                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUserId();

        returnCommandService.registReturn(franchiseId, vo, userId);

        return ResponseEntity.ok().build();
    }

}
