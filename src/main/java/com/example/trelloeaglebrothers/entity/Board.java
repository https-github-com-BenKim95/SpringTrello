package com.example.trelloeaglebrothers.entity;
//수정님

import com.example.trelloeaglebrothers.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board")
public class Board extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE)  // 중간 테이블 키주인 보드
    List<UserBoard> userBoards = new ArrayList<>();

    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE)
    List<ColumnList> columnLists;

    public Board(BoardRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.color = requestDto.getColor();
        this.description = requestDto.getDescription();
        this.userBoards = new ArrayList<>();
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.color = requestDto.getColor();
        this.description = requestDto.getDescription();
    }

}
