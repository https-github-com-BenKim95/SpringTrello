package com.example.trelloeaglebrothers.entity;

import com.example.trelloeaglebrothers.dto.ColumnListRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//추현중
@Entity
@Getter
@NoArgsConstructor
@Table(name = "column_list")
public class ColumnList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(nullable = false)
    private Long orderNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "columnList", cascade = CascadeType.REMOVE)
    private List<Card> cards = new ArrayList<>();

    public ColumnList(Board board, ColumnListRequestDto requestDto) {
        this.board = board;
        this.title = requestDto.getTitle();

    }

    public void update(String title, Board board) {
        this.board = board;
        this.title = title;

    }

    public void setOrderNum(Long orderNum) {

        this.orderNum = orderNum;
    }


}