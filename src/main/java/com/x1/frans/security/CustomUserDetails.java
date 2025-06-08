package com.x1.frans.security;

import com.x1.frans.user.command.aggregate.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final UserEntity user;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(UserEntity user, List<GrantedAuthority> authorities) {
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
        return authorities;
    }

    public Integer getUserId() {
        return user.getId();
    }

    public String getProfileUrl() {
        return user.getProfileUrl();
    }

    public Boolean getIsTempPassword() {
        return user.getIsTempPassword();
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
