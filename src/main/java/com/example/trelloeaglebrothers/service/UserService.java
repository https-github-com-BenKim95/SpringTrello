package com.example.trelloeaglebrothers.service;

import com.example.trelloeaglebrothers.dto.PasswordRequestDto;
import com.example.trelloeaglebrothers.dto.SignupDto;
import com.example.trelloeaglebrothers.dto.UserUpdateResponseDto;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.jwt.JwtUtil;
import com.example.trelloeaglebrothers.repository.UserBoardRepository;
import com.example.trelloeaglebrothers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserBoardRepository userBoardRepository;

    //회원가입
    public void signup(SignupDto signupDto) {
        String username = signupDto.getUsername();
        String password = signupDto.getPassword();
        String passwordCheck = signupDto.getCheckPassword();
        String email = signupDto.getEmail();
        String nickName = signupDto.getNickName();

        //닉네임과 같은 값이 비밀번호에 포함된 경우 회원가입 실패
        boolean usernameCheck = password.contains(username);
        if (usernameCheck) {
            throw new IllegalArgumentException("비밀번호에 유저네임을 포함할 수 없습니다.");
        }

        //비밀번호 일치여부 확인
        if(!password.equals(passwordCheck)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        String passwordEncode = passwordEncoder.encode(signupDto.getPassword());

        User user = new User(username, passwordEncode, email, nickName);
        userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    //비밀번호 확인
    public Boolean checkPassword(User user, PasswordRequestDto passwordRequestDto) {
        String password = user.getPassword();
        String checkPassword = passwordRequestDto.getPassword();

        return passwordEncoder.matches(checkPassword, password);
    }


    //마이페이지 수정
    @Transactional
    public UserUpdateResponseDto updateUser(SignupDto signupDto) {
        String username = signupDto.getUsername();
        String password = signupDto.getPassword();
        String passwordCheck = signupDto.getCheckPassword();
        String email = signupDto.getEmail();
        String nickName = signupDto.getNickName();

        Optional<User> findUser = userRepository.findByUsername(signupDto.getUsername());

        User user = findUser.orElseThrow(
                () -> new IllegalArgumentException("일치하는 회원이 없습니다."));

        //비밀번호 일치여부 확인
        if((password != null) && (password.equals(passwordCheck))) {
            user.setPassword(passwordEncoder.encode(password));
        }

        user.update(signupDto);
        return new UserUpdateResponseDto(user);
    }

    @Transactional
    public void delete(User user) {
        userBoardRepository.deleteAllByCollaborator(user);
        userRepository.delete(user);
    }
}