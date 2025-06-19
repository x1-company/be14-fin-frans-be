package com.x1.frans.statistics.command.application.controller;

import com.x1.frans.statistics.command.application.dto.FranchiseReturnProductStatDTO;
import com.x1.frans.statistics.command.application.dto.FranchiseReturnProductStatDeleteDTO;
import com.x1.frans.statistics.command.application.dto.FranchiseReturnProductStatModifyDTO;
import com.x1.frans.statistics.command.application.service.FranchiseReturnProductStatManualService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/admin/statistics/franchise/return-product")
@Tag(name = "📊 월별 가맹점 자재별 반품량 통계", description = "임의로 통계 데이터를 등록, 수정, 삭제하는 API")
public class FranchiseReturnProductStatManualController {

    private final FranchiseReturnProductStatManualService franchiseReturnProductStatManualService;

    @Autowired
    public FranchiseReturnProductStatManualController(
            FranchiseReturnProductStatManualService franchiseReturnProductStatManualService) {
        this.franchiseReturnProductStatManualService = franchiseReturnProductStatManualService;
    }

    @PostMapping("/create")
    @Operation(summary = "통계 데이터 생성", description = "관리자가 임의로 통계 데이터를 생성한다.")
    public void createManualStat(
            @Valid @RequestBody FranchiseReturnProductStatDTO franchiseReturnProductStatDTO) {

        franchiseReturnProductStatManualService.addStat(franchiseReturnProductStatDTO);

    }

    @PutMapping("/modify")
    @Operation(summary = "통계 데이터 수정", description = "관리자가 임의로 통계 데이터를 수정한다.")
    public void modifyManualStat(
            @Valid @RequestBody FranchiseReturnProductStatModifyDTO franchiseReturnProductStatModifyDTO) {

        franchiseReturnProductStatManualService.modifyStat(franchiseReturnProductStatModifyDTO);

    }

    @DeleteMapping("/delete")
    @Operation(summary = "통계 데이터 삭제", description = "관리자가 임의로 통계 데이터를 삭제한다.")
    public void deleteManualStat(
            @Valid @RequestBody FranchiseReturnProductStatDeleteDTO franchiseReturnProductStatDeleteDTO) {

        franchiseReturnProductStatManualService.deleteStat(franchiseReturnProductStatDeleteDTO);

    }
}
