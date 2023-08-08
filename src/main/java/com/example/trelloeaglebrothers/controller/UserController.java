package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.SignupDto;
import com.example.trelloeaglebrothers.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupDto signupDto, BindingResult bindingResult) {
        log.info("signup={}", signupDto);

        if(bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return new ResponseEntity<>("회원가입 형식에 맞지 않습니다.", HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            userService.signup(signupDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        }

        return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
    }
}
