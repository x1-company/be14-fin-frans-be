package com.x1.frans.approval.query.service;

import com.x1.frans.approval.query.dto.FranchiseApprovalCountDTO;
import com.x1.frans.approval.query.dto.FranchiseApprovedApprovalCountDTO;
import com.x1.frans.approval.query.repository.FranchiseApprovalCountMapper;
import java.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FranchiseApprovalCountService {

    private final FranchiseApprovalCountMapper mapper;

    @Autowired
    public FranchiseApprovalCountService(FranchiseApprovalCountMapper mapper) {
        this.mapper = mapper;
    }

    public FranchiseApprovalCountDTO getInProgressApprovalCounts(Long userId) {
        int orderCount = mapper.countInProgressOrderApprovals(userId);
        int returnCount = mapper.countInProgressReturnApprovals(userId);
        return new FranchiseApprovalCountDTO(orderCount, returnCount);
    }

    public FranchiseApprovedApprovalCountDTO getApprovedApprovalCounts(Long userId) {

        YearMonth thisMonth = YearMonth.now();
        YearMonth lastMonth = thisMonth.minusMonths(1);

        int thisMonthCount = mapper.countApprovedApprovalsByMonth(userId, thisMonth.getYear(), thisMonth.getMonthValue());
        int lastMonthCount = mapper.countApprovedApprovalsByMonth(userId, lastMonth.getYear(), lastMonth.getMonthValue());

        return new FranchiseApprovedApprovalCountDTO(thisMonthCount, lastMonthCount);
    }

}
