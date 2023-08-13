package com.example.trelloeaglebrothers.exception;

import com.example.trelloeaglebrothers.dto.ApiResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @valid 유효성체크에 통과하지 못하면 MethodArgumentNotValidException 이 발생한다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto> methodValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn("MethodArgumentNotValidException 발생!!! url:{}, trace:{}", request.getRequestURI(), e.getStackTrace());
        return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(), HttpStatus.BAD_REQUEST);
    }
}