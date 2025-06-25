package com.x1.frans.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.x1.frans.redis.service.RedisService;
import com.x1.frans.security.config.properties.TokenProperties;
import com.x1.frans.security.util.JwtUtil;
import com.x1.frans.security.vo.RequestLoginVO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final TokenProperties tokenProperties;
    private final RedisService redisService;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                JwtUtil jwtUtil,
                                TokenProperties tokenProperties,
                                RedisService redisService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.tokenProperties = tokenProperties;
        this.redisService = redisService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            // json을 Object로 매핑
            RequestLoginVO creds = new ObjectMapper().readValue(request.getInputStream(), RequestLoginVO.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getUserCode(), creds.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        String userCode = userDetails.getUsername();
        long expirationMillis = tokenProperties.getRefreshExpirationTime();

        String accessToken = jwtUtil.generateAccessToken(userDetails);

        response.addHeader("Authorization", "Bearer " + accessToken);

        String refreshToken = jwtUtil.generateRefreshToken(userCode);

        redisService.save("RT", userCode, refreshToken, expirationMillis);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                // TODO: 배포 시 변경 필요 (auth...AuthCommandController와 동일하게)
                .httpOnly(true)
                .secure(true) // HTTPS
                .path("/")
                .maxAge(expirationMillis / 1000)
//                .sameSite("Lax")
                .sameSite("None") // HTTPS
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("needChangePassword", userDetails.getIsTempPassword());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        responseBody.put("userType", userDetails.getUserType());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=utf-8");
        new ObjectMapper().writeValue(response.getWriter(), responseBody);
    }
}
