package com.x1.frans.supplier.query.controller;

import com.x1.frans.supplier.command.aggregate.SupplierEntity;
import com.x1.frans.supplier.query.dto.SupplierDetailDTO;
import com.x1.frans.supplier.query.service.SupplierQueryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supplier")
public class SupplierQueryController {

    public final SupplierQueryService supplierQueryService;

    @Autowired
    public SupplierQueryController(SupplierQueryService supplierQueryService) {
        this.supplierQueryService = supplierQueryService;
    }

    @Operation(summary = "공급처 상세 조회", description = "공급처 상세 조회합니다.")
    @GetMapping("/detail/{supplierId}")
    public ResponseEntity<SupplierDetailDTO> detail(@PathVariable("supplierId") int supplierId) {
        SupplierDetailDTO detail = supplierQueryService.getSupplierDetail(supplierId);

        return ResponseEntity.ok(detail);
    }
}
