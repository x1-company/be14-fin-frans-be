package com.x1.frans.auth.command.application.controller;

import com.x1.frans.auth.command.application.service.AuthCommandService;
import com.x1.frans.auth.command.application.vo.ChangePasswordRequestVO;
import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.user.command.service.UserCommandService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthCommandController {

    private final AuthCommandService authCommandService;
    private final UserCommandService userCommandService;

    @Autowired
    public AuthCommandController(AuthCommandService authCommandService,
                                 UserCommandService userCommandService) {
        this.authCommandService = authCommandService;
        this.userCommandService = userCommandService;
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

        userCommandService.changePassword(customUserDetails.getUserId(), changePasswordRequestVO);

        return ResponseEntity.ok().build();
    }
}
