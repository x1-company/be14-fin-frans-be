package com.x1.frans.auth.command.application.controller;

import com.x1.frans.auth.command.application.service.AuthCommandService;
import com.x1.frans.auth.command.application.vo.ChangePasswordRequestVO;
import com.x1.frans.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthCommandController {

    private final AuthCommandService authCommandService;

    @Autowired
    public AuthCommandController(AuthCommandService authCommandService) {
        this.authCommandService = authCommandService;
    }

    @PostMapping("/reissue")
    public ResponseEntity<Void> reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = authCommandService.extractRefreshTokenFromCookie(request);

        String newAccessToken = authCommandService.reissueAccessToken(refreshToken);

        response.setHeader("Authorization", "Bearer " + newAccessToken);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequestVO changePasswordRequestVO,
                                               @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        authCommandService.changePassword(customUserDetails.getUserId(), changePasswordRequestVO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                       @RequestHeader(value = "Authorization", required = false) String accessToken,
                                       HttpServletResponse response) {

        String token = null;
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            token = accessToken.substring(7);
        }

        authCommandService.logout(customUserDetails.getUsername(), token);

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                // TODO: 배포 시 변경 필요 (security.AuthenticationFilter와 동일하게)
                .httpOnly(true)
//                .secure(true) // HTTPS
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
//                .sameSite("None") // HTTPS
                .build();

        response.setHeader("Set-Cookie", deleteCookie.toString());

        return ResponseEntity.ok().build();
    }
}
