package com.example.trelloeaglebrothers.dto;

import com.example.trelloeaglebrothers.entity.User;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateResponseDto {

    private String username;

    private String email;

    private String nickName;

    public UserUpdateResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
    }
}
