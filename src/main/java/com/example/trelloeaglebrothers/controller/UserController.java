package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.SignupDto;
import com.example.trelloeaglebrothers.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping("/api/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
            return "signUp";

        }

        return "index";
    }
}
