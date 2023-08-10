package com.example.trelloeaglebrothers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BoardRequestDto {
    @NotBlank(message = "필수 입력 값입니다.")
    private String title;
    @NotBlank(message = "필수 입력 값입니다.")
    private String color;
    @NotBlank(message = "필수 입력 값입니다.")
    private String description;

    private boolean manager = false;

    @Getter
    @AllArgsConstructor
    public static class inviteInfoDto {
        String username;
        boolean isUser;
    }
}
