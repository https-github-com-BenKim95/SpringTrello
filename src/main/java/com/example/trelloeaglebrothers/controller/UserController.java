package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.PasswordRequestDto;
import com.example.trelloeaglebrothers.dto.SignupDto;
import com.example.trelloeaglebrothers.dto.UserUpdateResponseDto;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.jwt.JwtUtil;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import com.example.trelloeaglebrothers.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // 회원가입 페이지 이동
    @GetMapping("/users/signup")
    public String signUp(Model model) {
        model.addAttribute("signupDto", new SignupDto());
        return "signUp";
    }

    //회원가입
    @PostMapping("/api/users/signup")
    public String signup(@Validated @ModelAttribute SignupDto signupDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
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


    // 로그인 페이지 이동
    @GetMapping("/users/login")
    public String login() {
        return "login";
    }

    //비밀번호 확인 페이지 이동
    @GetMapping("/users/checkPassword")
    public String checkPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

        model.addAttribute("username", userDetails.getUsername());
        return "checkPassword";
    }

    // 비밀번호 일치 후 마이페이지로 이동
    // 비밀번호 불일치 현재 페이지
    @PostMapping("/api/users/password")
    public String myPage( @AuthenticationPrincipal UserDetailsImpl userDetails, PasswordRequestDto passwordRequestDto, Model model) {
        User user = userDetails.getUser();
        if (userService.checkPassword(user, passwordRequestDto)) {
            model.addAttribute("user", user);
            return "myPage";
        };
        model.addAttribute("username", user.getUsername());
        model.addAttribute("error", "비밀번호를 확인해주세요");
        return "checkPassword";
    }


    // 회원 정보 수정
    @PostMapping("/api/users/myPage")
    public String updateUser(@Validated @ModelAttribute SignupDto signupDto, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails, BindingResult bindingResult) {
        log.info("signup={}", signupDto);

        User user = userDetails.getUser();

        if(bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            model.addAttribute("user", user);
            return "myPage";
        }

        UserUpdateResponseDto userUpdateResponseDto = userService.updateUser(signupDto);
        model.addAttribute("user", userUpdateResponseDto);
        return "myPage";
    }

    // 로그아웃 API
    @GetMapping("/users/signout")
    public String signOut(HttpServletResponse response) {
        jwtUtil.expireCookie(response);
        return "redirect:/index";
    }

    //회원탈퇴
    @GetMapping("/users/delete")
    public String delete(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        User user = userDetails.getUser();
        userService.delete(user);
        jwtUtil.expireCookie(response);
        return "index";
    }
}
