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

    @ManyToMany(mappedBy = "boards", fetch = FetchType.EAGER)
    List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    List<ColumnList> columnLists;

    public Board(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
    }

    public Board(BoardRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.users = new ArrayList<>();
        this.users.add(user);
        user.getBoards().add(this);
    }

    public void addUser(User user) {
        users.add(user);
        user.getBoards().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getBoards().remove(this);
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
    }

}


/**
 * 위 코드에서는 User 클래스와 Board 클래스 간에 ManyToMany 관계를 설정하기 위해 연결 테이블인 user_board를 사용하였습니다.
 * 이를 통해 사용자가 여러 보드에 속할 수 있고, 보드도 여러 사용자에게 속할 수 있습니다.
 * addUser와 removeUser 메서드는 보드에 사용자를 추가하거나 제거할 때 사용됩니다.
 * 이 메서드들을 호출하여 사용자와 보드의 관계를 조작할 수 있습니다.
 */