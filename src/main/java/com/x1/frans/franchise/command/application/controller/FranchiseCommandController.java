package com.x1.frans.franchise.command.application.controller;

import com.x1.frans.franchise.command.application.service.FranchiseCommandService;
import com.x1.frans.franchise.command.domain.vo.UpdateFranchiseRequestVO;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "가맹점", description = "가맹점 관련 API")
@RestController
@RequestMapping("/api/franchise")
public class FranchiseCommandController {

    private final FranchiseCommandService franchiseCommandService;

    @Autowired
    public FranchiseCommandController(FranchiseCommandService franchiseCommandService) {
        this.franchiseCommandService = franchiseCommandService;
    }


    @Operation(summary = "가맹점 정보 수정", description = "가맹점 정보를 수정합니다.")
    @PutMapping("{franchiseId}/update")
    public ResponseEntity<Void> UpdateFranchiseInfo(@PathVariable("franchiseId") int franchiseId,
                                                    @RequestBody UpdateFranchiseRequestVO vo,
                                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        long userId = customUserDetails.getUserId();

        franchiseCommandService.updateFranchiseInfo(franchiseId, vo, userId);
        
        return ResponseEntity.noContent().build();
    }

}