package com.example.trelloeaglebrothers.entity;
//수정님

import com.example.trelloeaglebrothers.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class Board extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE)  // 중간 테이블 키주인 보드
    List<UserBoard> userBoards = new ArrayList<>(); // 보드 참여자(매니저, 멤버)

    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE)
    List<ColumnList> columnLists;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public Board(BoardRequestDto requestDto, User author, UserRoleEnum role) {
        this.title = requestDto.getTitle();
        this.color = requestDto.getColor();
        this.description = requestDto.getDescription();
        this.userBoards = new ArrayList<>();
        this.role = role;
        this.author = author;
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.color = requestDto.getColor();
        this.description = requestDto.getDescription();
        this.role = role;
    }

}
