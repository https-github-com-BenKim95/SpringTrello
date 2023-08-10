package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 메인페이지
    @GetMapping("/")
    public String Home(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            model.addAttribute("nickName", user.getNickName());
        }
        return "index";
    }

    @GetMapping("/memberMain")
    public String memberMain(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            model.addAttribute("nickName", user.getNickName());
        }
        return "memberMain";
    }

    @GetMapping("/card")
    public String card(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            model.addAttribute("nickName", user.getNickName());
        }
        return "card";
    }

    @GetMapping("/newBoard")
    public String newBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            model.addAttribute("nickName", user.getNickName());
        }
        return "newBoard";
    }

    @GetMapping("/editBoard")
    public String editBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            model.addAttribute("nickName", user.getNickName());
        }
        return "editBoard";
    }
}
