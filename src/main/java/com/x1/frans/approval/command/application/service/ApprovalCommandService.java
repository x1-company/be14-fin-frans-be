package com.x1.frans.approval.command.application.service;

import com.x1.frans.approval.command.application.dto.ApprovalCreateResponseDTO;
import com.x1.frans.approval.command.application.dto.ApprovalCreateRequestDTO;

public interface ApprovalCommandService {
    ApprovalCreateResponseDTO createApproval(ApprovalCreateRequestDTO request, long userId);
}
