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
}
