package com.x1.frans.returns.query.controller;

import com.x1.frans.franchise.query.service.FranchiseQueryService;
import com.x1.frans.returns.query.dto.ShippedOrderDTO;
import com.x1.frans.returns.query.service.ReturnQueryService;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "가맹점", description = "가맹점 관련 API")
@RestController
@RequestMapping("api/franchise")
public class ReturnQueryController {

    private final ReturnQueryService returnQueryService;

    @Autowired
    public ReturnQueryController(ReturnQueryService returnQueryService, FranchiseQueryService franchiseQueryService) {
        this.returnQueryService = returnQueryService;
    }

    @Operation(summary = "반품 등록 시 주문 목록 조회", description = "반품 등록 시 해당 가맹점의 주문 목록 중 최근 1주일 이내 배송이 완료된 목록을 조회할 수 있다.")
    @GetMapping("{franchiseId}/return/regist/orders")
    public ResponseEntity<List<ShippedOrderDTO>> findDeliveredOrdersWithinLastWeek
                                                (@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @PathVariable("franchiseId") Long franchiseId) {
        Long userId = userDetails.getUserId();
        List<ShippedOrderDTO> list = returnQueryService.findDeliveredOrdersWithinLastWeek(userId, franchiseId);

        System.out.println(userId);
        System.out.println(franchiseId);


        return ResponseEntity.ok(list);

    }

}
