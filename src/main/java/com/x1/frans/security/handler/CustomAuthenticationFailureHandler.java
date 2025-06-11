package com.x1.frans.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.x1.frans.exception.dto.ErrorResponseDTO;
import com.x1.frans.exception.enums.ErrorCode;
import com.x1.frans.security.exception.AccountDeletedException;
import com.x1.frans.security.exception.AccountLockedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomAuthenticationFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");

        String message = getMessage(exception);

        String errorCode = getErrorCode(exception);

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                errorCode,
                message);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }

    private static String getMessage(AuthenticationException exception) {
        if (exception instanceof BadCredentialsException
            || exception instanceof AccountLockedException
            || exception instanceof AccountDeletedException
            || exception instanceof UsernameNotFoundException) {
            return exception.getMessage();
        }
        return "로그인에 실패했습니다.";
    }

    private static String getErrorCode(AuthenticationException exception) {
        if (exception instanceof AccountLockedException)
            return ErrorCode.ACCOUNT_LOCKED.getCode();
        if (exception instanceof AccountDeletedException)
            return ErrorCode.ACCOUNT_DELETED.getCode();

        return ErrorCode.AUTH_FAILED.getCode();
    }
}
