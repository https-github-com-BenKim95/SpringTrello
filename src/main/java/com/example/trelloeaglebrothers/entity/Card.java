package com.example.trelloeaglebrothers.entity;

import com.example.trelloeaglebrothers.dto.CardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

//동규님
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "card")
public class Card extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String color;

    // 날짜 설정 필드 추가 -> JSON으로 {2023-09-12T15:30} 와 같이 입력 보내야함
    @Column
    private LocalDateTime dueDate;

    // 다대다 관계를 중간 엔티티인 UserCard를 통해 설정하여 불러오기
    // 작업자 설정
    @OneToMany(mappedBy = "card")
    private List<UserCard> userCardList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_list_id")
    private ColumnList columnList;


    public Card (String title, String description, String color, LocalDateTime dueDate, List<UserCard> userCardList, ColumnList columnList) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.dueDate = dueDate;
        this.userCardList = userCardList;
        this.columnList = columnList;
    }
}
