package com.x1.frans.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.x1.frans.exception.dto.ErrorResponseDTO;
import com.x1.frans.exception.enums.ErrorCode;
import com.x1.frans.security.util.JwtUtil;
import com.x1.frans.user.query.service.UserQueryService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserQueryService userQueryService;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,
                                   UserQueryService userQueryService,
                                   ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.userQueryService = userQueryService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.resolveAccessToken(request);

        if (accessToken != null) {
            try {
                jwtUtil.validateToken(accessToken);

                String userCode = jwtUtil.getUserCode(accessToken);
                UserDetails userDetails = userQueryService.loadUserByUsername(userCode);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException e) {
                sendErrorResponse(response, ErrorCode.TOKEN_EXPIRED, "토큰이 만료되었습니다.");
                return;
            } catch (JwtException | IllegalArgumentException e) {
                sendErrorResponse(response, ErrorCode.INVALID_TOKEN, "유효하지 않은 토큰입니다.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response,
                                   ErrorCode errorCode,
                                   String message) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(errorCode.getCode(), message);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
