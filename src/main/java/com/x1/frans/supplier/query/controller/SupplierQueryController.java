package com.x1.frans.supplier.query.controller;

import com.x1.frans.supplier.query.dto.SupplierListDTO;
import com.x1.frans.supplier.query.service.SupplierQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "공급처", description = "공급처 관련 API")
@RestController
@RequestMapping("/supplier")
@Slf4j
public class SupplierQueryController {
    private final SupplierQueryService supplierQueryService;

    public SupplierQueryController(SupplierQueryService supplierQueryService) {
        this.supplierQueryService = supplierQueryService;
    }

    @Operation(summary = "공급처 목록 조회", description = "공급처 리스트를 이름순으로 조회한다.")
    @GetMapping("/list")
    public ResponseEntity<List<SupplierListDTO>> supplierList() {

        List<SupplierListDTO> list = supplierQueryService.getAllSuppliers();
        return ResponseEntity.ok(list);
    }
}
