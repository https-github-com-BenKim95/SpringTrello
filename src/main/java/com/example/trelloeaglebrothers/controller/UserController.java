package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.user.PasswordRequestDto;
import com.example.trelloeaglebrothers.dto.user.SignupRequestDto;
import com.example.trelloeaglebrothers.dto.user.UserUpdateRequestDto;
import com.example.trelloeaglebrothers.dto.user.UserUpdateResponseDto;
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


    // 로그인 페이지 이동
    @GetMapping("/users/login")
    public String login() {
        return "login";
    }

    // 회원가입 페이지 이동
    @GetMapping("/users/signup")
    public String signUp(Model model) {
        model.addAttribute("signupRequestDto", new SignupRequestDto());
        return "signUp";
    }

    //회원가입
    @PostMapping("/api/users/signup")
    public String signup(@Validated @ModelAttribute SignupRequestDto signupRequestDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        //검증 오류 확인 후 에러 발생시 리턴
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "signUp";
        }
        //특정 조건 오류 검증 후 실패시 리턴
        userService.signup(signupRequestDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "signUp";
        }
        return "index";
    }

    // 로그아웃 API
    @GetMapping("/users/logout")
    public String logout(HttpServletResponse response) {
        jwtUtil.expireCookie(response);
        return "redirect:/index";
    }

    //비밀번호 확인 페이지 이동
    @GetMapping("/users/checkPassword")
    public String checkPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        return "user/checkPassword";
    }

    // 비밀번호 일치 -> 마이페이지로 이동
    // 비밀번호 불일치 -> 비밀번호 페이지
    @PostMapping("/api/users/password")
    public String myPage(@AuthenticationPrincipal UserDetailsImpl userDetails, PasswordRequestDto passwordRequestDto, Model model) {
        User user = userDetails.getUser();

        //비밀번호 일치 -> 마이페이지
        if (userService.checkPassword(user, passwordRequestDto)) {
            model.addAttribute("userUpdateRequestDto", new UserUpdateRequestDto(user));
            return "user/myPage";
        }

        //비밀번호 불일치 -> 비밀번호  페이지
        model.addAttribute("username", user.getUsername());
        model.addAttribute("error", "비밀번호를 확인해주세요");
        return "user/checkPassword";
    }

    //정보 수정(닉네임, 이메일)
    @PostMapping("/api/users/myPage")
    public String updateUser(@Validated @ModelAttribute UserUpdateRequestDto userUpdateRequestDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "user/myPage";
        }

        UserUpdateResponseDto userUpdateResponseDto = userService.updateUser(userUpdateRequestDto);
        model.addAttribute("userUpdateRequestDto", userUpdateResponseDto);
        return "user/myPage";
    }

    //비밀번호 변경 페이지 이동
    @GetMapping("/users/updatePassword")
    public String updateMovePassword(Model model) {
        model.addAttribute("passwordRequestDto", new PasswordRequestDto());
        return "user/updatePassword";
    }

    //비밀번호 변경
    @PostMapping("/api/users/updatePassword")
    public String updatePassword(@Validated @ModelAttribute PasswordRequestDto passwordRequestDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "user/updatePassword";
        }
        userService.updatePassword(passwordRequestDto, userDetails.getUser(), bindingResult);
        if (bindingResult.hasErrors()) {
            return "user/updatePassword";
        }
        return "redirect:/?success=password";
    }

    //회원탈퇴 페이지로 이동
    @GetMapping("/users/delete")
    public String moveDeleteUser(@AuthenticationPrincipal  UserDetailsImpl userDetails, Model model) {
        User user = userDetails.getUser();
        model.addAttribute("username", user.getUsername());
        return "user/deleteUser";
    }

    //회원탈퇴
    @PostMapping("/api/users/delete")
    public String deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails,@ModelAttribute PasswordRequestDto passwordRequestDto, Model model, HttpServletResponse response) {
        User user = userDetails.getUser();
        if (userService.checkPassword(user, passwordRequestDto)) {
            userService.delete(user);
            jwtUtil.expireCookie(response);
            return "redirect:/?success=delete";
        }
        model.addAttribute("username", user.getUsername());
        model.addAttribute("error", "비밀번호를 확인해주세요");
        return "user/deleteUser";
    }
}
