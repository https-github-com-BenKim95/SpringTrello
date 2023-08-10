package com.example.trelloeaglebrothers.dto;

import com.example.trelloeaglebrothers.entity.CardComment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CardCommentResponseDto {
    private String comment;


    public CardCommentResponseDto(CardComment cardComment) {
        this.comment = cardComment.getComment();
    }
}
