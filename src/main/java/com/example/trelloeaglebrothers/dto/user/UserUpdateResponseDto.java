package com.example.trelloeaglebrothers.dto.user;

import com.example.trelloeaglebrothers.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateResponseDto {

    private String username;
    private String nickName;
    private String email;


    public UserUpdateResponseDto(User user) {
        this.username = user.getUsername();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
    }
}
