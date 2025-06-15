package com.x1.frans.returns.query.controller;

import com.x1.frans.franchise.query.service.FranchiseQueryService;
import com.x1.frans.returns.query.dto.ProductOrderDTO;
import com.x1.frans.returns.query.dto.ReturnSearchConditionDTO;
import com.x1.frans.returns.query.dto.ReturnSearchPageDTO;
import com.x1.frans.returns.query.dto.ShippedOrderDTO;
import com.x1.frans.returns.query.service.ReturnQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "🔄 반품", description = "반품 관련 API")
@RestController
@RequestMapping("api/franchise/return")
public class ReturnQueryController {

    private final ReturnQueryService returnQueryService;

    @Autowired
    public ReturnQueryController(ReturnQueryService returnQueryService) {
        this.returnQueryService = returnQueryService;
    }

    @Operation(summary = "반품 등록 시 주문 목록 조회", description = "반품 등록 시 해당 가맹점의 주문 목록 중 최근 1주일 이내 배송이 완료된 목록을 조회할 수 있다.")
    @GetMapping("{franchiseId}/regist/orders")
    public ResponseEntity<List<ShippedOrderDTO>> findDeliveredOrdersWithinLastWeek
                                                (@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @PathVariable("franchiseId") Long franchiseId) {
        Long userId = userDetails.getUserId();
        List<ShippedOrderDTO> list = returnQueryService.findDeliveredOrdersWithinLastWeek(userId, franchiseId);

        return ResponseEntity.ok(list);

    }

    @Operation(summary = "반품 등록 시 주문서 자재 목록 조회", description = "반품 등록 시 주문서의 자재 목록을 조회할 수 있다.")
    @GetMapping("{franchiseId}/regist/orders/{orderId}")
    public ResponseEntity<List<ProductOrderDTO>> findProductListByOrderId
            (@AuthenticationPrincipal CustomUserDetails userDetails,
             @PathVariable("franchiseId") Long franchiseId,
             @PathVariable("orderId") Long orderId) {
        Long userId = userDetails.getUserId();
        List<ProductOrderDTO> list = returnQueryService.findProductListByOrderId(userId, franchiseId, orderId);

        return ResponseEntity.ok(list);

    }

    @GetMapping
    @Operation(summary = "반품 목록 조회",
            description = "가맹점주는 본인 가맹점에 한해서 반품 목록을 전체 조회할 수 있다")
    public ResponseEntity<ReturnSearchPageDTO> findAllReturns(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute ReturnSearchConditionDTO condition) {

        Long userId = userDetails.getUserId();

        ReturnSearchPageDTO list = returnQueryService.findAllReturns(userId, condition);

        return ResponseEntity.ok(list);
    }
}
