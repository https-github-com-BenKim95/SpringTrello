package com.example.trelloeaglebrothers.dto;

import com.example.trelloeaglebrothers.entity.Card;
import com.example.trelloeaglebrothers.entity.UserCard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CardResponseDto {
    private Long card_id;
    private String title;
    private String description;
    private String color;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<String> workerList;
    private List<CardCommentResponseDto> cardCommentList;


    public CardResponseDto(Card card) {
        this.card_id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.color = card.getColor();
        this.dueDate = card.getDueDate();
        this.createdAt = card.getCreatedAt();
        this.modifiedAt = card.getModifiedAt();
        this.workerList = memberList(card.getUserCardList());
        this.cardCommentList = card.getCardComments().stream().map(CardCommentResponseDto::new).collect(Collectors.toList());
    }

    // List<UserCard>를 Response에서 Username만 나오게함
    private List<String> memberList(List<UserCard> userCardList) {
        List<String> usernames = new ArrayList<>();
        for (UserCard userCard : userCardList) {
            usernames.add(userCard.getUser().getUsername());
        }
        return usernames;
    }
}
