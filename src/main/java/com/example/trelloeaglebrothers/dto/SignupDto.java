package com.example.trelloeaglebrothers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignupDto {

    @Pattern(regexp = "^[A-Za-z0-9]{4,15}$", message = "아이디는 4~15 글자의 영문 숫자 조합이어야 합니다.")
    private String username;

    @Pattern(regexp = "^[A-Za-z0-9]{4,15}$", message = "비밀번호는 4~15 글자의 영문 숫자 조합이어야 합니다.")
    private String password;

    //비밀번호 확인
    @Pattern(regexp = "^[A-Za-z0-9]{4,15}$", message = "비밀번호는 4~15 글자의 영문 숫자 조합이어야 합니다.")
    private String checkPassword;

    @Email
    private String email;

    private String nickName;

}
