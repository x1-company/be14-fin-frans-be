package com.x1.frans.statistics.command.application.controller;

import com.x1.frans.statistics.command.application.dto.FranchiseOrderAmountStatDTO;
import com.x1.frans.statistics.command.application.dto.FranchiseOrderAmountStatDeleteDTO;
import com.x1.frans.statistics.command.application.dto.FranchiseOrderAmountStatModifyDTO;
import com.x1.frans.statistics.command.application.service.FranchiseOrderAmountStatManualService;
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
@RequestMapping("/api/admin/statistics/franchise/order-amount")
@Tag(name = "📊 통계", description = "임의로 통계 데이터를 등록, 수정, 삭제하는 API")
public class FranchiseOrderAmountStatManualController {

    private final FranchiseOrderAmountStatManualService franchiseOrderAmountStatManualService;

    @Autowired
    public FranchiseOrderAmountStatManualController(
            FranchiseOrderAmountStatManualService franchiseOrderAmountStatManualService) {
        this.franchiseOrderAmountStatManualService = franchiseOrderAmountStatManualService;
    }

    @PostMapping("/create")
    @Operation(summary = "통계 데이터 생성", description = "관리자가 임의로 통계 데이터를 생성한다.")
    public void createManualStat(
            @Valid @RequestBody FranchiseOrderAmountStatDTO franchiseOrderAmountStatDTO) {

        franchiseOrderAmountStatManualService.addStat(franchiseOrderAmountStatDTO);

    }

    @PutMapping("/modify")
    @Operation(summary = "통계 데이터 수정", description = "관리자가 임의로 통계 데이터를 수정한다.")
    public void modifyManualStat(
            @Valid @RequestBody FranchiseOrderAmountStatModifyDTO franchiseOrderAmountStatModifyDTO) {

        franchiseOrderAmountStatManualService.modifyStat(franchiseOrderAmountStatModifyDTO);

    }

    @DeleteMapping("/delete")
    @Operation(summary = "통계 데이터 삭제", description = "관리자가 임의로 통계 데이터를 삭제한다.")
    public void deleteManualStat(
            @Valid @RequestBody FranchiseOrderAmountStatDeleteDTO franchiseOrderAmountStatDeleteDTO) {

        franchiseOrderAmountStatManualService.deleteStat(franchiseOrderAmountStatDeleteDTO);
    }

}
