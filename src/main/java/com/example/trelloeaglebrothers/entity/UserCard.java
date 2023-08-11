package com.example.trelloeaglebrothers.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

//추현중
@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_card")
public class UserCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    public UserCard (User user, Card card) {
        this.user = user;
        this.card = card;
    }
}