package com.x1.frans.auth.command.application.controller;

import com.x1.frans.auth.command.application.service.AuthCommandService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
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

    @PostMapping("/test")
    public ResponseEntity<Void> testAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().build();
    }
}
