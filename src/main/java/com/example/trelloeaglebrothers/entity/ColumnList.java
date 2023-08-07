package com.example.trelloeaglebrothers.entity;

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

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "board_id")
    private Board board;



}
