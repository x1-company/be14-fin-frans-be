package com.x1.frans.statistics.query.rawdata.service;

import com.x1.frans.statistics.query.rawdata.dto.FranchiseOrderAmountRawDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface FranchiseOrderAmountRawQueryService {

    List<FranchiseOrderAmountRawDTO> getOrderAmounts(LocalDateTime from, LocalDateTime to);

}
