package com.x1.frans.user.query.service;

import com.x1.frans.exception.UnauthorizedAccessException;
import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.security.exception.AccountDeletedException;
import com.x1.frans.security.exception.AccountLockedException;
import com.x1.frans.user.enums.DepartmentType;
import com.x1.frans.user.query.dto.*;
import com.x1.frans.user.query.repository.UserQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserQueryMapper userQueryMapper;

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

        LoginUserDTO loginUser = userQueryMapper.findByCode(userCode);

        if (loginUser == null) {
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        }

        if (loginUser.getIsDeleted()) {
            throw new AccountDeletedException("탈퇴한 계정입니다.");
        }

        if (loginUser.getIsLocked()) {
            throw new AccountLockedException("잠긴 계정입니다. 관리자에게 문의하거나 비밀번호를 초기화 해주세요.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        return new CustomUserDetails(loginUser, grantedAuthorities);
    }

    @Override
    public List<SearchHqUserDTO> findHqUser(String name, Long departmentId) {

        List<Long> departmentIds = DepartmentType.getSelfAndAllSubDepartmentIds(departmentId);

        return userQueryMapper.findHqUser(name, departmentIds);
    }

    @Override
    public List<SearchFranchiseUserDTO> findFranchiseUser(String name) {

        return userQueryMapper.findFranchiseUser(name);
    }

    @Override
    public List<SearchSupplierUserDTO> findSupplierUser(String name) {

        return userQueryMapper.findSupplierUser(name);
    }

    @Override
    public HqUserDepartmentDTO getDepartmentInfo(Long userId) {

        return userQueryMapper.getDepartmentInfo(userId);
    }

    @Override
    public List<Long> getAccessibleFranchiseIdsForUser(Long userId) {
        HqUserDepartmentDTO dept = getDepartmentInfo(userId);
        if (dept == null || dept.getDepartmentId() == null) {
            throw new UnauthorizedAccessException("해당 사용자의 부서 정보를 찾을 수 없습니다.");
        }

        return userQueryMapper.findFranchiseIdsByDepartmentId(dept.getDepartmentId());
    }

}
