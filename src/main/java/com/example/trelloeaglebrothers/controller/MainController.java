package com.example.trelloeaglebrothers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {

    // 메인페이지
    @GetMapping("/")
    public String Home() {
        return "index";
    }

    @GetMapping("/users/signup")
    public String signUp() {
        return "signUp";
    }
}
