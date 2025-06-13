package com.x1.frans.order.command.application.controller;

import com.x1.frans.exception.InvalidTimeFormatException;
import com.x1.frans.order.command.application.dto.OrderRejectRequestDto;
import com.x1.frans.order.command.application.service.HqOrderCommandService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hq/orders")
@Tag(name = "📝 주문 처리", description = "주문 상태 변경, 처리 관련 API")
public class HqOrderCommandController {

    private final HqOrderCommandService hqOrderCommandService;

    @PostMapping("/deadline")
    @Operation(
            summary = "주문 마감 시간 등록 또는 수정",
            description = "주문 마감 시간을 등록하거나 기존 시간을 수정합니다. 파라미터는 HH:mm 형식의 문자열입니다."
    )
    public ResponseEntity<String> createOrUpdateDeadline(
            @Parameter(description = "주문 마감 시간 (형식: HH:mm)", example = "10:30")
            @RequestParam("time") String time
    ) {
        try {
            LocalTime deadlineTime = LocalTime.parse(time);
            boolean isCreated = hqOrderCommandService.createOrUpdateDeadline(deadlineTime);
            String message = isCreated ? "주문 마감 시간이 등록되었습니다." : "주문 마감 시간이 변경되었습니다.";
            return ResponseEntity.status(isCreated ? 201 : 200).body(message);
        } catch (DateTimeParseException e) {
            throw new InvalidTimeFormatException("시간 형식이 올바르지 않습니다. 예: 10:30");
        }

    }

    @PatchMapping("/{orderId}/reject")
    @Operation(
            summary = "주문 반려 처리",
            description = "검토 중이거나 검토 완료 상태인 주문건을 반려 처리합니다."
    )
    public ResponseEntity<Void> rejectOrder(
            @PathVariable Long orderId,
            @RequestBody @Valid OrderRejectRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        hqOrderCommandService.rejectOrder(orderId, requestDto.getReason(), userDetails.getUserId());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{orderId}/review-complete")
    @Operation(
            summary = "주문 상태 변경 (검토 중 -> 검토 완료)",
            description = "검토중인 주문을 검토 완료로 변경합니다."
    )
    public ResponseEntity<Void> completeReview(
            @PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        hqOrderCommandService.markReviewComplete(orderId, userDetails.getUserId());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{orderId}/review-cancel")
    @Operation(
            summary = "주문 상태 변경 (검토 완료 -> 검토 중)",
            description = "검토 완료된 주문을 검토 취소 처리하여 검토 중으로 변경합니다."
    )
    public ResponseEntity<Void> cancelReview(
            @PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        hqOrderCommandService.cancelReviewComplete(orderId, userDetails.getUserId());

        return ResponseEntity.ok().build();
    }
}
