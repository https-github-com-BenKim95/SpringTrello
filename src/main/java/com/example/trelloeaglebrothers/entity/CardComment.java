package com.example.trelloeaglebrothers.entity;

import com.example.trelloeaglebrothers.dto.CardCommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//장원님
@Entity
@Table(name = "card_comments")
@Getter
@Setter
@RequiredArgsConstructor
public class CardComment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;


    public CardComment (CardCommentRequestDto cardCommentRequestDto, User user, Card card) {
        this.comment = cardCommentRequestDto.getComment();
        this.user = user;
        this.card = card;
    }
}
