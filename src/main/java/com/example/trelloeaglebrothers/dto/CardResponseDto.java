package com.example.trelloeaglebrothers.dto;

import com.example.trelloeaglebrothers.entity.Card;
import com.example.trelloeaglebrothers.entity.UserCard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CardResponseDto {
    private Long card_id;
    private String title;
    private String description;
    private String color;
    private LocalDateTime dueDate;
    List<UserCard> userCardList;

    public CardResponseDto (Card card) {
        this.card_id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.color = card.getColor();
        this.dueDate = card.getDueDate();
        this.userCardList = card.getUserCardList();
    }
}
