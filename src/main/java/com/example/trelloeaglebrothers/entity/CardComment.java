package com.example.trelloeaglebrothers.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
//장원님
@Entity
@Table(name = "cardComments")
@Getter
@NoArgsConstructor
public class CardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; 
    
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;
}
