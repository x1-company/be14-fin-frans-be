package com.x1.frans.user.query.service;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.security.exception.AccountDeletedException;
import com.x1.frans.security.exception.AccountLockedException;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.query.dto.SearchFranchiseUserDTO;
import com.x1.frans.user.query.dto.SearchHqUserDTO;
import com.x1.frans.user.query.dto.SearchSupplierUserDTO;
import com.x1.frans.user.query.repository.UserQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {

        UserEntity loginUser = userQueryMapper.findByCode(userCode);

        if (loginUser == null) {
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        }

        if (loginUser.getIsDeleted()) {
            throw new AccountDeletedException("탈퇴한 계정입니다.");
        }

        if (loginUser.getIsLocked()) {
            throw new AccountLockedException("잠긴 계정입니다. 관리자에게 문의해 주세요.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // TODO: 조회한 회원 별 권한 추가 로직 작성 필요
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return new CustomUserDetails(loginUser, grantedAuthorities);
    }

    @Override
    public List<SearchHqUserDTO> findHqUser(String name, Long departmentId) {

        return userQueryMapper.findHqUser(name, departmentId);
    }

    @Override
    public List<SearchFranchiseUserDTO> findFranchiseUser(String name) {

        return userQueryMapper.findFranchiseUser(name);
    }

    @Override
    public List<SearchSupplierUserDTO> findSupplierUser(String name) {

        return userQueryMapper.findSupplierUser(name);
    }
}
