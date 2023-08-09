package com.example.trelloeaglebrothers.entity;

import com.example.trelloeaglebrothers.dto.ColumnListRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

//추현중
@Entity
@Getter
@NoArgsConstructor
@Table(name = "column_list")
public class ColumnList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long orderNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


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
