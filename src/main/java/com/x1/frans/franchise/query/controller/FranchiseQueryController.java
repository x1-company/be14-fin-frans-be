package com.x1.frans.franchise.query.controller;

import com.x1.frans.exception.FranchiseNotFoundException;
import com.x1.frans.franchise.query.dto.FranchiseDetailDTO;
import com.x1.frans.franchise.query.dto.FranchiseListDTO;
import com.x1.frans.franchise.query.service.FranchiseQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 가맹점 목록을 가맹점 이름순으로 조회할 수 있다.
     * @return List<FranchiseListDTO> 가맹점 목록이 담긴 DTO 리스트
     */
    @Operation(summary = "가맹점 목록 조회", description = "가맹점 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<FranchiseListDTO>> findAllFranchise() {

        List<FranchiseListDTO> list = franchiseQueryService.findAllFranchise();

        return ResponseEntity.ok(list);
    }

    /**
     * ID에 해당하는 가맹점 정보를 상세 조회할 수 있다.
     * @param franchiseId 가맹점 ID
     * @return FranchiseDetailDTO 가맹점 상세 정보가 담긴 DTO
     */
    @Operation(summary = "가맹점 상세 조회", description = "가맹점 정보를 상세 조회합니다.")
    @GetMapping("{franchiseId}")
    public ResponseEntity<FranchiseDetailDTO> findFranchiseDetailById(@PathVariable("franchiseId") Long franchiseId) {

        FranchiseDetailDTO detail = franchiseQueryService.findFranchiseDetailById(franchiseId);

        if (detail == null) {
            throw new FranchiseNotFoundException("해당 가맹점이 존재하지 않습니다.");
        }

        return ResponseEntity.ok(detail);
    }

}
