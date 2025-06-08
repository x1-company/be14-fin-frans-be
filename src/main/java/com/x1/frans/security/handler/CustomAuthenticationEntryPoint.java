package com.x1.frans.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.x1.frans.exception.dto.ErrorResponseDTO;
import com.x1.frans.exception.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ErrorCode.INVALID_TOKEN.getCode(),
                "인증이 필요합니다. 토큰이 없거나 유효하지 않습니다."
        );
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
