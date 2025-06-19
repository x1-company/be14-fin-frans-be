package com.x1.frans.statistics.command.application.service;

import com.x1.frans.statistics.command.application.dto.FranchiseOrderAmountStatDTO;
import com.x1.frans.statistics.command.application.dto.FranchiseOrderAmountStatDeleteDTO;
import com.x1.frans.statistics.command.application.dto.FranchiseOrderAmountStatModifyDTO;
import jakarta.validation.Valid;

public interface FranchiseOrderAmountStatManualService {

    void addStat(@Valid FranchiseOrderAmountStatDTO franchiseOrderAmountStatDTO);

    void modifyStat(@Valid FranchiseOrderAmountStatModifyDTO franchiseOrderAmountStatModifyDTO);

    void deleteStat(@Valid FranchiseOrderAmountStatDeleteDTO franchiseOrderAmountStatDeleteDTO);

}
