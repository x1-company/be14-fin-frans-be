package com.x1.frans.supplier.command.application.controller;

import com.x1.frans.exception.dto.ErrorResponseDTO;
import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.supplier.command.application.service.SupplierCommandService;
import com.x1.frans.supplier.command.vo.SupplierUpdateRequestVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "🐔 공급처", description = "공급처 관련 API")
@RestController
@RequestMapping("/api/hq/suppliers")
@Slf4j
public class SupplierCommandController {

    private final SupplierCommandService supplierCommandService;

    @Autowired
    public SupplierCommandController(SupplierCommandService supplierCommandService) {
        this.supplierCommandService = supplierCommandService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "공급처 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (필수 항목 누락 또는 유효성 검사 실패)",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "공급처를 찾을 수 없음, 접근 권한 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "공급처 정보 수정", description = "공급처 정보를 수정합니다.")
    @PutMapping("/modification/{supplierId}")
    public ResponseEntity<Void> updateSupplier(@PathVariable("supplierId") long supplierId,
                                               @RequestBody SupplierUpdateRequestVO supplierUpdateRequestVO,
                                               @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        long userId = customUserDetails.getUserId();
        supplierCommandService.updateSupplier(userId, supplierId, supplierUpdateRequestVO);

        return ResponseEntity.ok().build();
    }
}
