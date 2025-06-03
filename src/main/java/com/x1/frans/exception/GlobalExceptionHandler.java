package com.x1.frans.exception;

import com.x1.frans.exception.dto.ErrorResponseDTO;
import com.x1.frans.exception.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomException(BaseCustomException e) {
        ErrorCode code = e.getErrorCode();
        log.error("CustomException - {}", code.getCode(), e);

        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setErrorCode(code.getCode());
        response.setMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(code.getHttpStatus()).body(response);
    }
}
