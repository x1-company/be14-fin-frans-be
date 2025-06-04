package com.x1.frans.user.query.service;

public interface UserQueryService {

    String findLatestUserCode(String userType, String codePrefix);

    boolean isEmailExist(String email);

    boolean isPhoneExist(String phone);
}
