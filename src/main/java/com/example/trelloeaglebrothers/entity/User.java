package com.example.trelloeaglebrothers.entity;

import com.example.trelloeaglebrothers.dto.SignupDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private String nickName;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

//    @Builder.Default
    @OneToMany(mappedBy = "collaborator", orphanRemoval = true)
    private List<UserBoard> boardUsers = new ArrayList<>();

//    @Builder.Default
    @OneToMany(mappedBy = "author", orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();


    public User(String username, String password, String email, String nickName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
    }

    public User(UserRoleEnum userRoleEnum) {
        this.role = userRoleEnum;
    }

    public void update(SignupDto signupDto) {
        this.email = signupDto.getEmail();
        this.nickName = signupDto.getNickName();
    }
}
