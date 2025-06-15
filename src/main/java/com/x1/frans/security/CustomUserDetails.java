package com.x1.frans.security;

import com.x1.frans.user.enums.UserType;
import com.x1.frans.user.query.dto.LoginUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final LoginUserDTO user;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(LoginUserDTO user, List<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return user.getCode();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getType().name()));
    }

    public Long getUserId() {
        return user.getId();
    }

    public String getProfileUrl() {
        return user.getProfileUrl();
    }

    public Boolean getIsTempPassword() {
        return user.getIsTempPassword();
    }

    public UserType getUserType() {
        return user.getType();
    }

    public Long getDepartmentId() {
        return user.getDepartmentId();
    }

    public Long getPositionId() {
        return user.getPositionId();
    }

    public Long getDutyId() {
        return user.getDutyId();
    }

    public Long getSupplierId() {
        return user.getSupplierId();
    }

    public Long getFranchiseId() {
        return user.getFranchiseId();
    }

    // 주의. org.springframework.boot version 3.5.0 => 3.2.5로 변경하면서 필수적으로 오버라딩해야 하는 메소드들
    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
