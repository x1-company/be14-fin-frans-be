package com.x1.frans.approval.query.service;

import com.x1.frans.approval.query.dto.ApprovalListDTO;
import com.x1.frans.approval.query.repository.ApprovalQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalQueryServiceImpl implements ApprovalQueryService {

    private final ApprovalQueryMapper approvalQueryMapper;

    @Autowired
    public ApprovalQueryServiceImpl(ApprovalQueryMapper approvalQueryMapper) {
        this.approvalQueryMapper = approvalQueryMapper;
    }

    @Override
    public List<ApprovalListDTO> getApprovalListNewest(long userId) {
        return approvalQueryMapper.getApprovalListNewest(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListOldest(long userId) {
        return approvalQueryMapper.getApprovalListOldest(userId);
    }
}
