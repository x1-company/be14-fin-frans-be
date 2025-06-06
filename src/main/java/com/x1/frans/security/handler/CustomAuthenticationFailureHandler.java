package com.x1.frans.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.x1.frans.security.exception.AccountDeletedException;
import com.x1.frans.security.exception.AccountLockedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");

        String message = getMessage(exception);

        String body = new ObjectMapper().writeValueAsString(Map.of(
                "errorCode", "AUTH_FAILED",
                "message", message,
                "timestamp", LocalDateTime.now().toString()
        ));

        response.getWriter().write(body);
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
}
