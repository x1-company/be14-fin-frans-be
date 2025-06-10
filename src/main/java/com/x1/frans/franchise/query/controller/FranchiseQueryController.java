package com.x1.frans.franchise.query.controller;

import com.x1.frans.franchise.query.dto.FranchiseListDTO;
import com.x1.frans.franchise.query.service.FranchiseQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "가맹점", description = "가맹점 관련 API")
@RestController
@RequestMapping("/api/franchise")
public class FranchiseQueryController {

    private final FranchiseQueryService franchiseQueryService;

    @Autowired
    public FranchiseQueryController(FranchiseQueryService franchiseQueryService) {
        this.franchiseQueryService = franchiseQueryService;
    }

    @Operation(summary = "가맹점 목록 조회", description = "가맹점 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<FranchiseListDTO>> findAllFranchise() {

        List<FranchiseListDTO> list = franchiseQueryService.findAllFranchise();

        return ResponseEntity.ok(list);
    }

}
