package com.example.trelloeaglebrothers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupDto {

    @NotBlank
    @Size(min=3, max=15)
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String username;

    @NotBlank
    @Size(min=8, max=15)
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+$")
    private String password;

    //비밀번호 확인
    @NotBlank
    @Size(min=4, max=15)
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+$")
    private String checkPassword;

    @Email
    private String email;



}
