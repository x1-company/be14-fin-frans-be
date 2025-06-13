package com.x1.frans.franchise.query.controller;

import com.x1.frans.franchise.query.dto.FranchiseDetailDTO;
import com.x1.frans.franchise.query.dto.FranchiseListDTO;
import com.x1.frans.franchise.query.service.FranchiseQueryService;
import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.user.query.service.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "🏬 가맹점", description = "가맹점 관련 API")
@RestController
@RequestMapping("/api/hq/franchise")
public class HqFranchiseQueryController {

    private final FranchiseQueryService franchiseQueryService;

    @Autowired
    public HqFranchiseQueryController(FranchiseQueryService franchiseQueryService) {
        this.franchiseQueryService = franchiseQueryService;
    }

    @Operation(summary = "부서별 가맹점 목록 조회", description = "자신이 속한 부서가 담당하는 가맹점 목록을 조회할 수 있다")
    @GetMapping("/department")
    public ResponseEntity<List<FranchiseListDTO>> findFranchisesByDepartmentId(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        List<FranchiseListDTO> list = franchiseQueryService.findFranchisesByDepartmentId(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "담당자별 가맹점 목록 조회", description = "자신이 담당하는 가맹점 목록을 조회할 수 있다")
    @GetMapping("/manager")
    public ResponseEntity<List<FranchiseListDTO>> findFranchisesByManagerId(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        List<FranchiseListDTO> list = franchiseQueryService.findFranchisesByManagerId(userId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "가맹점 상세 조회", description = "열람 권한이 있는 가맹점에 한해서 가맹점 정보를 상세 조회할 수 있다.")
    @GetMapping("{franchiseId}")
    public ResponseEntity<FranchiseDetailDTO> findHqFranchiseDetailById(
            @PathVariable("franchiseId") Long franchiseId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();

        FranchiseDetailDTO detail = franchiseQueryService.findHqFranchiseDetailById(franchiseId, userId);

        return ResponseEntity.ok(detail);
    }
}

