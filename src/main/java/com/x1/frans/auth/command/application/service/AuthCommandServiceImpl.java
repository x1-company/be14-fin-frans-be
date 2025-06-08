package com.x1.frans.auth.command.application.service;

import com.x1.frans.exception.ExpiredRefreshTokenException;
import com.x1.frans.exception.InvalidRefreshTokenException;
import com.x1.frans.redis.service.RedisService;
import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.security.util.JwtUtil;
import com.x1.frans.user.query.service.UserQueryService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthCommandServiceImpl implements AuthCommandService {

    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private final UserQueryService userQueryService;

    @Autowired
    public AuthCommandServiceImpl(JwtUtil jwtUtil, RedisService redisService, UserQueryService userQueryService) {
        this.jwtUtil = jwtUtil;
        this.redisService = redisService;
        this.userQueryService = userQueryService;
    }

    @Override
    public String extractRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new InvalidRefreshTokenException("Refresh token이 존재하지 않습니다.");
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token이 존재하지 않습니다."));
    }

    @Override
    public String reissueAccessToken(String refreshToken) {

        try {
            jwtUtil.validateToken(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new ExpiredRefreshTokenException("Refresh token이 만료되었습니다.");
        } catch (JwtException e) {
            throw new InvalidRefreshTokenException("유효하지 않은 Refresh token 입니다.");
        }

        String userCode = jwtUtil.getUserCode(refreshToken);
        String redisRefreshToken = redisService.get("RT", userCode);

        if (redisRefreshToken == null || !redisRefreshToken.equals(refreshToken)) {
            throw new InvalidRefreshTokenException("유효하지 않은 Refresh token 입니다.");
        }

        UserDetails userDetails = userQueryService.loadUserByUsername(userCode);

        return jwtUtil.generateAccessToken((CustomUserDetails) userDetails);
    }
}
