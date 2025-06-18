package com.x1.frans.statistics.command.application.service;

import com.x1.frans.statistics.command.application.dto.FranchiseReturnProductStatDTO;
import com.x1.frans.statistics.command.application.dto.FranchiseReturnProductStatDeleteDTO;
import com.x1.frans.statistics.command.application.dto.FranchiseReturnProductStatModifyDTO;
import jakarta.validation.Valid;

public interface FranchiseReturnProductStatManualService {

    void addStat(@Valid FranchiseReturnProductStatDTO franchiseReturnProductStatDTO);

    void modifyStat(@Valid FranchiseReturnProductStatModifyDTO franchiseReturnProductStatModifyDTO);

    void deleteStat(@Valid FranchiseReturnProductStatDeleteDTO franchiseReturnProductStatDeleteDTO);

}
