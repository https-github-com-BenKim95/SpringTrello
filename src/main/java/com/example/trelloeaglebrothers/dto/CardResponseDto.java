package com.example.trelloeaglebrothers.dto;

import com.example.trelloeaglebrothers.entity.Card;
import com.example.trelloeaglebrothers.entity.UserCard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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


    public CardResponseDto(Card card) {
        this.card_id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.color = card.getColor();
        this.dueDate = card.getDueDate();
        this.createdAt = card.getCreatedAt();
        this.modifiedAt = card.getModifiedAt();
        this.workerList = workerLists(card.getUserCardList());
    }

    // List<UserCard>를 Response에서 Username만 나오게함
    private List<String> workerLists(List<UserCard> userCardList) {
        List<String> usernames = new ArrayList<>();
        for (UserCard userCard : userCardList) {
            usernames.add(userCard.getUser().getUsername());
        }
        return usernames;
    }
}
