package com.x1.frans.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.x1.frans.exception.dto.ErrorResponseDTO;
import com.x1.frans.exception.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json; charset=UTF-8");

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ErrorCode.FORBIDDEN.getCode(),
                "해당 요청에 대한 권한이 없습니다."
        );
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
