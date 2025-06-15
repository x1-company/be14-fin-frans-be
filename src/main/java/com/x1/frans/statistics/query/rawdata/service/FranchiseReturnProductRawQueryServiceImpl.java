package com.x1.frans.statistics.query.rawdata.service;

import com.x1.frans.statistics.query.rawdata.dto.FranchiseReturnProductRawDTO;
import com.x1.frans.statistics.query.rawdata.repository.FranchiseReturnProductRawDataMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FranchiseReturnProductRawQueryServiceImpl implements FranchiseReturnProductRawQueryService {

    private final FranchiseReturnProductRawDataMapper franchiseReturnProductRawDataMapper;

    @Autowired
    public FranchiseReturnProductRawQueryServiceImpl(
                            FranchiseReturnProductRawDataMapper franchiseReturnProductRawDataMapper) {
        this.franchiseReturnProductRawDataMapper = franchiseReturnProductRawDataMapper;
    }

    @Override
    public List<FranchiseReturnProductRawDTO> getReturnProducts(LocalDateTime from, LocalDateTime to) {
        return franchiseReturnProductRawDataMapper.findTotalReturnProductByFranchise(from, to);
    }
}
