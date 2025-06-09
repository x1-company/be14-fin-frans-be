package com.x1.frans.user.command.service;

import com.x1.frans.auth.command.application.vo.ChangePasswordRequestVO;
import com.x1.frans.user.command.vo.FranchiseUserRequestVO;
import com.x1.frans.user.command.vo.HqUserRequestVO;
import com.x1.frans.user.command.vo.SupplierUserRequestVO;

public interface UserCommandService {
    void createHqUser(HqUserRequestVO hqUserRequestVO);

    void createFranchiseUser(FranchiseUserRequestVO franchiseUserRequestVO);

    void createSupplierUser(SupplierUserRequestVO supplierUserRequestVO);

    void changePassword(Integer userId, ChangePasswordRequestVO changePasswordRequestVO);
}
