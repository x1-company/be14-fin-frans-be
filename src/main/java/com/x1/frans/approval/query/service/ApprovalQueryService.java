package com.x1.frans.approval.query.service;

import com.x1.frans.approval.query.dto.ApprovalListDTO;

import java.util.List;

public interface ApprovalQueryService {
    List<ApprovalListDTO> getApprovalListNewest(long userId);

    List<ApprovalListDTO> getApprovalListOldest(long userId);
}
