package com.x1.frans.auth.command.application.service;

import com.x1.frans.auth.command.application.dto.ResetPasswordResponseDTO;
import com.x1.frans.auth.command.application.vo.ChangePasswordRequestVO;
import com.x1.frans.email.dto.UserCredentialsDTO;
import com.x1.frans.email.service.EmailService;
import com.x1.frans.exception.ExpiredRefreshTokenException;
import com.x1.frans.exception.InvalidPasswordException;
import com.x1.frans.exception.InvalidRefreshTokenException;
import com.x1.frans.exception.UserNotFoundException;
import com.x1.frans.redis.service.RedisService;
import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.security.util.JwtUtil;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.UserCommandRepository;
import com.x1.frans.user.query.service.UserQueryService;
import com.x1.frans.user.util.GenerateRandomPassword;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class AuthCommandServiceImpl implements AuthCommandService {

    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private final UserQueryService userQueryService;
    private final UserCommandRepository userCommandRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public AuthCommandServiceImpl(JwtUtil jwtUtil,
                                  RedisService redisService,
                                  UserQueryService userQueryService,
                                  UserCommandRepository userCommandRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                  PasswordEncoder passwordEncoder,
                                  EmailService emailService) {
        this.jwtUtil = jwtUtil;
        this.redisService = redisService;
        this.userQueryService = userQueryService;
        this.userCommandRepository = userCommandRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    /**
     * 쿠키에서 refreshToken 추출
     *
     * @param request 요청 객체
     */
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

    /**
     * accessToken 재발급
     *
     * @param refreshToken refreshToken
     */
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

    /**
     * 비밀번호 변경
     *
     * @param vo 비밀번호 변경 요청 정보
     */
    @Transactional
    @Override
    public void changePassword(Long userId, ChangePasswordRequestVO vo) {

        UserEntity user = userCommandRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저는 존재하지 않습니다."));

        if (!passwordEncoder.matches(vo.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("현재 비밀번호가 일치하지 않습니다.");
        }

        String newEncodedPassword = bCryptPasswordEncoder.encode(vo.getNewPassword());
        user.setPassword(newEncodedPassword);
        user.setIsTempPassword(false);
    }

    /**
     * 로그아웃
     *
     * @param userCode 사용자 코드
     */
    @Override
    public void logout(String userCode, String accessToken) {

        // refreshToken 제거
        if (redisService.exists("RT", userCode)) {
            redisService.remove("RT", userCode);
        }

        // accessToken 블랙리스트 등록
        if (accessToken != null && jwtUtil.isTokenValid(accessToken)) {

            long expirationMillis = jwtUtil.getRemainingExpiration(accessToken);
            redisService.save("BL", accessToken, "logout", expirationMillis);
        }
    }

    @Transactional
    @Override
    public ResetPasswordResponseDTO resetPassword(String userCode) {

        UserEntity user = userCommandRepository.findByCode(userCode)
                .orElseThrow(() -> new UserNotFoundException("해당 유저는 존재하지 않습니다."));

        String rawPassword = GenerateRandomPassword.generate();
        String encryptedPassword = bCryptPasswordEncoder.encode(rawPassword);

        user.setPassword(encryptedPassword);

        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
        userCredentialsDTO.setTo(user.getEmail());
        userCredentialsDTO.setName(user.getName());
        userCredentialsDTO.setUserCode(userCode);
        userCredentialsDTO.setRawPassword(rawPassword);

        emailService.sendUserCredentials(userCredentialsDTO, "PASSWORD_RESET");

        ResetPasswordResponseDTO resetPasswordResponseDTO = new ResetPasswordResponseDTO();
        resetPasswordResponseDTO.setMaskedEmail(maskEmail(user.getEmail()));
        resetPasswordResponseDTO.setMessage("해당 메일로 발송되었습니다. 이후 비밀번호를 꼭 변경해 주세요.");

        return resetPasswordResponseDTO;
    }

    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;

        String[] parts = email.split("@");
        String local = parts[0];
        String domain = parts[1];

        if (local.length() <= 2) {
            return "*@".concat(domain); // 너무 짧은 경우 전체 마스킹
        }

        String masked = local.charAt(0) +
                "*".repeat(Math.max(1, local.length() - 2)) +
                local.charAt(local.length() - 1);

        return masked + "@" + domain;
    }
}
