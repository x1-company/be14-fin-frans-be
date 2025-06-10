package com.x1.frans.order.command.application.controller;

import com.x1.frans.exception.InvalidTimeFormatException;
import com.x1.frans.order.command.application.service.StoreOrderDeadlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hq/orders/deadline")
@Tag(name = "📝 주문", description = "orders")
public class StoreOrderDeadlineController {

    private final StoreOrderDeadlineService storeOrderDeadlineService;

    @PostMapping
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
            boolean isCreated = storeOrderDeadlineService.createOrUpdateDeadline(deadlineTime);
            String message = isCreated ? "주문 마감 시간이 등록되었습니다." : "주문 마감 시간이 변경되었습니다.";
            return ResponseEntity.status(isCreated ? 201 : 200).body(message);
        } catch (DateTimeParseException e) {
            throw new InvalidTimeFormatException("시간 형식이 올바르지 않습니다. 예: 10:30");
        }

    }
}
