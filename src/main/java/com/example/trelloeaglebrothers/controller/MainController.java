package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.SignupDto;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/boardDetails")
    public String boardDetail(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            model.addAttribute("nickName", user.getNickName());
        }
        return "boardDetails";
    }

}
