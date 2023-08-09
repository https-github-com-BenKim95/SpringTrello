package com.example.trelloeaglebrothers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {

    private String title;
    private String color;
    private String description;
    private boolean manager = false;

    @Getter
    @AllArgsConstructor
    public static class inviteInfoDto {
        String username;
        boolean isUser;
    }
}
