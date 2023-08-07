package com.example.trelloeaglebrothers.entity;
//수정님

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board")
public class Board extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    public Board(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "board")
    List<UserBoard> userBoards = new ArrayList<>();

    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE)
    List<ColumnList> columnLists;





}
