package com.x1.frans.approval.query.service;

import com.x1.frans.approval.query.dto.ApprovalListDTO;

import java.util.List;

public interface ApprovalQueryService {
    List<ApprovalListDTO> getApprovalList(long userId);
}
