package com.x1.frans.user.query.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserQueryService extends UserDetailsService {

    String findLatestUserCode(String userType, String codePrefix);

    boolean isEmailExist(String email);

    boolean isPhoneExist(String phone);
}
