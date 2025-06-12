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
    public List<ApprovalListDTO> getApprovalListSubmitted(long userId) {
        return approvalQueryMapper.getApprovalListSubmitted(userId);
    }


    @Override
    public List<ApprovalListDTO> getApprovalListDraft(long userId) {
        return approvalQueryMapper.getApprovalListDraft(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListInProgress(long userId) {
        return approvalQueryMapper.getApprovalListInProgress(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListApproved(long userId) {
        return approvalQueryMapper.getApprovalListApproved(userId);
    }

    @Override
    public List<ApprovalListDTO> getApprovalListRejected(long userId) {
        return approvalQueryMapper.getApprovalListRejected(userId);
    }
}
