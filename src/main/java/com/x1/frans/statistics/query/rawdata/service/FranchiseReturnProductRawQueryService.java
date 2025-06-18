package com.x1.frans.statistics.query.rawdata.service;

import com.x1.frans.statistics.query.rawdata.dto.FranchiseReturnProductRawDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface FranchiseReturnProductRawQueryService {

    List<FranchiseReturnProductRawDTO> getReturnProducts(LocalDateTime from, LocalDateTime to);

}
