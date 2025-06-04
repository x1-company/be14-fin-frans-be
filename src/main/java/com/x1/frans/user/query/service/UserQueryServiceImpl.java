package com.x1.frans.user.query.service;

import com.x1.frans.user.query.repository.UserQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserQueryMapper userQueryMapper;

    @Autowired
    public UserQueryServiceImpl(UserQueryMapper userQueryMapper) {
        this.userQueryMapper = userQueryMapper;
    }

    @Override
    public String findLatestUserCode(String userType, String codePrefix) {
        return userQueryMapper.findLatestUserCode(userType, codePrefix);
    }

    @Override
    public boolean isEmailExist(String email) {
        return userQueryMapper.isEmailExist(email);
    }

    @Override
    public boolean isPhoneExist(String phone) {
        return userQueryMapper.isPhoneExist(phone);
    }
}
