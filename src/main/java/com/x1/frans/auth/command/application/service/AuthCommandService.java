package com.x1.frans.auth.command.application.service;

import com.x1.frans.auth.command.application.vo.ChangePasswordRequestVO;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthCommandService {
    String extractRefreshTokenFromCookie(HttpServletRequest request);

    String reissueAccessToken(String refreshToken);

    void changePassword(Long userId, ChangePasswordRequestVO changePasswordRequestVO);

    void logout(String username, String accessToken);
}
