package com.x1.frans.user.query.service;

import com.x1.frans.user.query.dto.SearchFranchiseUserDTO;
import com.x1.frans.user.query.dto.SearchHqUserDTO;
import com.x1.frans.user.query.dto.SearchSupplierUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserQueryService extends UserDetailsService {

    String findLatestUserCode(String userType, String codePrefix);

    boolean isEmailExist(String email);

    boolean isPhoneExist(String phone);

    List<SearchHqUserDTO> findHqUser(String name, Integer departmentId);

    List<SearchFranchiseUserDTO> findFranchiseUser(String name);

    List<SearchSupplierUserDTO> findSupplierUser(String name);
}
