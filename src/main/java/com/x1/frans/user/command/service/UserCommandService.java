package com.x1.frans.user.command.service;

import com.x1.frans.user.command.vo.FranchiseUserRequestVO;
import com.x1.frans.user.command.vo.HqUserRequestVO;

public interface UserCommandService {
    void createHqUser(HqUserRequestVO hqUserRequestVO);

    void createFranchiseUser(FranchiseUserRequestVO franchiseUserRequestVO);
}
