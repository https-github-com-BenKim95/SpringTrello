package com.example.trelloeaglebrothers.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

@RequiredArgsConstructor
@Getter
public class ApiResponseDto {
    private String message;
    private HttpStatusCode httpStatusCode;

    public ApiResponseDto (HttpStatusCode httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
