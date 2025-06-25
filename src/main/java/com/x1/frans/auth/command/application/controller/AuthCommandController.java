package com.x1.frans.auth.command.application.controller;

import com.x1.frans.auth.command.application.dto.ResetPasswordResponseDTO;
import com.x1.frans.auth.command.application.service.AuthCommandService;
import com.x1.frans.auth.command.application.vo.ChangePasswordRequestVO;
import com.x1.frans.auth.command.application.vo.ResetPasswordRequestVO;
import com.x1.frans.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "🔐 인증, 인가", description = "auth")
public class AuthCommandController {

    private final AuthCommandService authCommandService;

    @Autowired
    public AuthCommandController(AuthCommandService authCommandService) {
        this.authCommandService = authCommandService;
    }

    @PostMapping("/reissue")
    @Operation(
            summary = "accessToken 재발급",
            description = "accessToken 만료 시 refreshToken을 이용해서 accessToken 재발급"
    )
    public ResponseEntity<Void> reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = authCommandService.extractRefreshTokenFromCookie(request);

        String newAccessToken = authCommandService.reissueAccessToken(refreshToken);

        response.setHeader("Authorization", "Bearer " + newAccessToken);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    @Operation(
            summary = "비밀번호 변경",
            description = "이전 비밀번호가 일치하면 새로운 비밀번호로 변경되고 is_temp_password가 false로 변경"
    )
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequestVO changePasswordRequestVO,
                                               @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        authCommandService.changePassword(customUserDetails.getUserId(), changePasswordRequestVO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    @Operation(
            summary = "로그아웃",
            description = "refreshToken을 지우고 accessToken을 만료 시간까지 redis에 블랙리스트로 등록"
    )
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
                .secure(true) // HTTPS
                .path("/")
                .maxAge(0)
//                .sameSite("Lax")
                .sameSite("None") // HTTPS
                .build();

        response.setHeader("Set-Cookie", deleteCookie.toString());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password/reset")
    public ResponseEntity<ResetPasswordResponseDTO> resetPassword(@RequestBody ResetPasswordRequestVO vo) {

        ResetPasswordResponseDTO resetPasswordResponseDTO = authCommandService.resetPassword(vo.getUserCode());

        return ResponseEntity.ok(resetPasswordResponseDTO);
    }
}
