package com.x1.frans.auth.command.application.service;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthCommandService {
    String extractRefreshTokenFromCookie(HttpServletRequest request);

    String reissueAccessToken(String refreshToken);
}
