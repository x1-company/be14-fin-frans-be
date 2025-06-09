package com.x1.frans.franchise.command.application.service;

import com.x1.frans.franchise.command.domain.vo.UpdateFranchiseRequestVO;

public interface FranchiseCommandService {

    void updateFranchiseInfo(int franchiseId, UpdateFranchiseRequestVO vo, int userId);
}
