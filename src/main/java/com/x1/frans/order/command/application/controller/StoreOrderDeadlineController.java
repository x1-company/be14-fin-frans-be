package com.x1.frans.order.command.application.controller;

import com.x1.frans.order.command.application.service.StoreOrderDeadlineService;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deadline")
@RequiredArgsConstructor
public class StoreOrderDeadlineController {

    private final StoreOrderDeadlineService storeOrderDeadlineService;

    @PostMapping
    public ResponseEntity<String> createOrUpdateDeadline(
            @RequestParam("time")
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime deadlineTime) {
        boolean isCreated = storeOrderDeadlineService.createOrUpdateDeadline(deadlineTime);
        String message = isCreated ? "주문 마감 시간이 등록되었습니다." : "주문 마감 시간이 변경되었습니다.";
        return ResponseEntity.status(isCreated ? 201 : 200).body(message);
    }
}
