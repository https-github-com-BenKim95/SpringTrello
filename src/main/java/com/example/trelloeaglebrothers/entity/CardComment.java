package com.example.trelloeaglebrothers.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
//장원님
@Entity
@Table(name = "card_comments")
@Getter
@NoArgsConstructor
public class CardComment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id", nullable = false)
    private User user; 
    
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;
}
