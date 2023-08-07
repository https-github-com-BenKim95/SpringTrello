package com.example.trelloeaglebrothers.entity;

import jakarta.persistence.*;
import lombok.Getter;

//동규님
@Entity
@Getter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String color;

//    @Column(nullable = false)
//    private String title;
}
