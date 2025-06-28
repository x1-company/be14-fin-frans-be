package com.x1.frans.returns.command.application.controller;

import com.x1.frans.returns.command.application.service.ReturnCommandService;
import com.x1.frans.returns.command.domain.vo.*;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "🔄 반품", description = "반품 관련 API")
@RestController
@RequestMapping("/api/hq/return")
public class HqReturnCommandController {

    private final ReturnCommandService returnCommandService;

    @Autowired
    public HqReturnCommandController(ReturnCommandService returnCommandService) {
        this.returnCommandService = returnCommandService;
    }

    @Operation(summary = "반품 검토 (자재별 반품 상태 변경)", description ="자재별 반품 상태를 변경하여 반품 검토를 할 수 있다.")
    @PatchMapping("/{returnId}/review-completed")
    public ResponseEntity completeReview(@PathVariable("returnId") Long returnId,
                                       @RequestBody ReturnReviewCompleteRequestVO vo,
                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUserId();

        returnCommandService.completeReview(returnId, vo, userId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "반품 반려 상태 변경", description ="반품 상태를 반려로 변경할 수 있다.")
    @PatchMapping("/{returnId}/reject")
    public ResponseEntity reject(@PathVariable("returnId") Long returnId,
                                 @RequestBody ReturnRejectRequestVO vo,
                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUserId();

        returnCommandService.reject(returnId, vo, userId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "반품 회수 정보 입력", description ="반품 회수 정보를 입력할 수 있다.")
    @PatchMapping("/{returnId}/delivery")
    public ResponseEntity updateDeliveryInfo(@PathVariable("returnId") Long returnId,
                                 @RequestBody ReturnDeliveryInfoRequestVO vo,
                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUserId();

        returnCommandService.updateDeliveryInfo(returnId, vo, userId);

        return ResponseEntity.ok().build();
    }


    @Operation(summary = "반품 수거일 정보 입력", description ="반품 수거일 정보를 입력할 수 있다.")
    @PatchMapping("/{returnId}/delivered-at")
    public ResponseEntity updateDeliveredAt(@PathVariable("returnId") Long returnId,
                                             @RequestBody ReturnDeliveredAtVO vo,
                                             @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUserId();

        returnCommandService.updateDeliveredAt(returnId, vo, userId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "반품 수거 완료 상태 변경", description ="반품 수거 완료로 상태를 변경할 수 있다.")
    @PatchMapping("/{returnId}/complete")
    public ResponseEntity returnComplete(@PathVariable("returnId") Long returnId,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUserId();

        returnCommandService.returnComplete(returnId, userId);

        return ResponseEntity.ok().build();
    }


}
