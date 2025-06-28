package com.x1.frans.approval.query.service;

import com.x1.frans.approval.query.dto.FranchiseApprovalCountDTO;
import com.x1.frans.approval.query.repository.FranchiseApprovalCountMapper;
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

}
