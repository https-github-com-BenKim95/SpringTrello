package com.example.trelloeaglebrothers.service;

import com.example.trelloeaglebrothers.dto.user.PasswordRequestDto;
import com.example.trelloeaglebrothers.dto.user.SignupRequestDto;
import com.example.trelloeaglebrothers.dto.user.UserUpdateRequestDto;
import com.example.trelloeaglebrothers.dto.user.UserUpdateResponseDto;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.repository.UserBoardRepository;
import com.example.trelloeaglebrothers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserBoardRepository userBoardRepository;

    //회원가입
    public void signup(SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String checkPassword = signupRequestDto.getCheckPassword();
        String email = signupRequestDto.getEmail();
        String nickName = signupRequestDto.getNickName();

        //닉네임과 같은 값이 비밀번호에 포함된 경우 회원가입 실패
        boolean usernameCheck = password.contains(username);
        if (usernameCheck) {
            bindingResult.addError(new FieldError("signupDto", "password", "비밀번호에 유저네임을 포함할 수 없습니다."));
        }

        //가입시 비밀번호 입력, 비밀번호 확인 같은지 확인
        if (!password.equals(checkPassword)) {
            bindingResult.addError(new FieldError("signupDto", "password", "비밀번호가 일치하지 않습니다."));
        }

        //회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            bindingResult.addError(new FieldError("signupDto", "username", "중복된 사용자가 존재합니다."));
        }

        //에러 없을 때 회원가입 성공
        if (!bindingResult.hasErrors()) {
            String passwordEncode = passwordEncoder.encode(signupRequestDto.getPassword());
            User user = new User(username, passwordEncode, email, nickName);
            userRepository.save(user);
        }
    }

    //비밀번호 확인
    public Boolean checkPassword(User user, PasswordRequestDto passwordRequestDto) {
        String password = user.getPassword();
        String checkPassword = passwordRequestDto.getPassword();

        return passwordEncoder.matches(checkPassword, password);
    }

    //정보 수정(닉네임, 이메일)
    @Transactional
    public UserUpdateResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        Optional<User> findUser = userRepository.findByUsername(userUpdateRequestDto.getUsername());

        User user = findUser.orElseThrow(
                () -> new IllegalArgumentException("일치하는 회원이 없습니다."));

        user.update(userUpdateRequestDto);
        return new UserUpdateResponseDto(user);
    }

    //비밀번호 변경
    public void updatePassword(PasswordRequestDto passwordRequestDto, User user, BindingResult bindingResult) {
        String username = user.getUsername();
        String password = passwordRequestDto.getPassword();
        String passwordCheck = passwordRequestDto.getCheckPassword();

        //닉네임과 같은 값이 비밀번호에 포함된 경우 회원가입 실패
        boolean usernameCheck = password.contains(username);
        if (usernameCheck) {
            bindingResult.addError(new FieldError("passwordRequestDto", "password", "비밀번호에 유저네임을 포함할 수 없습니다."));
        }

        //비밀번호 일치여부 확인
        if (!password.equals(passwordCheck)) {
            bindingResult.addError(new FieldError("passwordRequestDto", "password", "비밀번호가 일치하지 않습니다."));
        }

        String encode = passwordEncoder.encode(password);
        user.setPassword(encode);
        userRepository.save(user);
    }

    //회원탈퇴
    @Transactional
    public void delete(User user) {
        userBoardRepository.deleteAllByCollaborator(user);
        userRepository.delete(user);
    }
}