package com.x1.frans.supplier.query.controller;

import com.x1.frans.exception.InvalidSearchPeriodException;
import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.supplier.query.dto.HqDeliveryInfoDetailsDTO;
import com.x1.frans.supplier.query.dto.HqRequestedDeliveryInfoDTO;
import com.x1.frans.supplier.query.dto.HqSupplierDeliveryInfoDTO;
import com.x1.frans.supplier.query.service.HqSupplierDeliveryInfoQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "🐔 납품서 조회", description = "본사 직원이 공급처에서 작성한 납품서를 조회하는 API")
@Slf4j
@RestController
@RequestMapping("/api/hq/delivery-info")
public class HqDeliveryInfoQueryController {

    private final HqSupplierDeliveryInfoQueryService hqSupplierDeliveryInfoQueryService;

    @Autowired
    public HqDeliveryInfoQueryController(HqSupplierDeliveryInfoQueryService hqSupplierDeliveryInfoQueryService) {
        this.hqSupplierDeliveryInfoQueryService = hqSupplierDeliveryInfoQueryService;
    }



    @GetMapping("/list")
    @Operation(summary = "납품서 목록 조회", description = "납품서 목록을 연, 월, 일로 필터링하여 조회하는 API")
    public ResponseEntity<List<HqSupplierDeliveryInfoDTO>> getDeliveryInfos(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer day,
            @RequestParam(required = false) Long supplierId
    ) {
        Long userId = customUserDetails.getUserId();

        List<HqSupplierDeliveryInfoDTO> results
                = hqSupplierDeliveryInfoQueryService.findDeliveryInfos(userId, year, month, day, supplierId);

        return ResponseEntity.ok(results);
    }

    @GetMapping("/orders/{type}")
    @Operation(
            summary = "납품 정보 등록이 필요하거나 등록된 발주 목록 조회",
            description = "type: requested, completed | YearMonth: 'yyyyMM'"
    )
    public ResponseEntity<List<HqRequestedDeliveryInfoDTO>> getRequestedPurchaseOrders(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String type,
            @RequestParam(required = false) String startYearMonth,
            @RequestParam(required = false) String endYearMonth,
            @RequestParam(required = false) Long supplierId) {

        Long userId = customUserDetails.getUserId();

        if (startYearMonth == null || endYearMonth == null ||
                (Integer.parseInt(endYearMonth) - Integer.parseInt(startYearMonth) > 5)) {
            throw new InvalidSearchPeriodException("잘못된 기간입니다! 최대 6개월만 조회가 가능합니다.");
        }

        List<HqRequestedDeliveryInfoDTO> hqRequestedOrders = hqSupplierDeliveryInfoQueryService
                .getRequestedPurchaseOrders(userId, type, startYearMonth, endYearMonth, supplierId);

        return ResponseEntity.ok(hqRequestedOrders);
    }

    @GetMapping("/order/{purchaseOrderId}")
    @Operation(
            summary = "납품 정보 등록이 필요한 발주 상세 조회",
            description = "결재가 완료된 해당 공급처의 발주 상세 조회"
    )
    public ResponseEntity<HqDeliveryInfoDetailsDTO> getPurchaseOrderDetail(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long purchaseOrderId) {

        Long userId = customUserDetails.getUserId();

        HqDeliveryInfoDetailsDTO details = hqSupplierDeliveryInfoQueryService
                .getPurchaseOrderDetail(purchaseOrderId, userId);

        return ResponseEntity.ok(details);
    }
}
