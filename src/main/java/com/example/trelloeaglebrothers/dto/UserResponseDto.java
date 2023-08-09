package com.example.trelloeaglebrothers.dto;

import com.example.trelloeaglebrothers.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String username;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
    }
}
