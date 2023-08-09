package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.SignupDto;
import com.example.trelloeaglebrothers.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    //회원가입
    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute  SignupDto signupDto, BindingResult bindingResult) {
        log.info("signup={}", signupDto);

        if(bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "signUp";
        }

        try {
            userService.signup(signupDto);
        } catch (Exception e) {
            e.printStackTrace(); // 예외처리
            return "signUp";

        }

        return "index";
    }
}
