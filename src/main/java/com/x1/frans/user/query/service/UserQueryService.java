package com.x1.frans.user.query.service;

import com.x1.frans.user.query.dto.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserQueryService extends UserDetailsService {

    String findLatestUserCode(String userType, String codePrefix);

    boolean isEmailExist(String email);

    boolean isPhoneExist(String phone);

    List<SearchHqUserDTO> findHqUser(String name, Long departmentId);

    List<SearchFranchiseUserDTO> findFranchiseUser(String name);

    List<SearchSupplierUserDTO> findSupplierUser(String name);

    HqUserDepartmentDTO getDepartmentInfo(Long userId);

    List<Long> getAccessibleFranchiseIdsForUser(Long userId);

    MyInfoDTO getMyInfo(Long userId);
}
