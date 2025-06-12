package com.x1.frans.statistics.query.rawdata.service;

import com.x1.frans.statistics.query.rawdata.dto.FranchiseOrderAmountRawDTO;
import com.x1.frans.statistics.query.rawdata.repository.FranchiseOrderAmountRawDataMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FranchiseOrderAmountRawQueryServiceImpl implements FranchiseOrderAmountRawQueryService {

    private final FranchiseOrderAmountRawDataMapper rawDataMapper;

    @Autowired
    public FranchiseOrderAmountRawQueryServiceImpl(FranchiseOrderAmountRawDataMapper rawDataMapper) {
        this.rawDataMapper = rawDataMapper;
    }

    @Override
    public List<FranchiseOrderAmountRawDTO> getOrderAmounts(LocalDateTime from, LocalDateTime to) {
        return rawDataMapper.findTotalOrderAmountByFranchise(from, to);
    }

}
