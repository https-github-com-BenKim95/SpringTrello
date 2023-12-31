package com.example.trelloeaglebrothers.entity;

import com.example.trelloeaglebrothers.dto.user.SignupRequestDto;
import com.example.trelloeaglebrothers.dto.user.UserUpdateRequestDto;
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


    @OneToMany(mappedBy = "collaborator", orphanRemoval = true)
    private List<UserBoard> boardUsers = new ArrayList<>();


    @OneToMany(mappedBy = "author", orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();


    public User(String username, String password, String email, String nickName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickName = nickName;

    }


    public void update(UserUpdateRequestDto userUpdateRequestDto) {
        this.email = userUpdateRequestDto.getEmail();
        this.nickName = userUpdateRequestDto.getNickName();
    }
}
