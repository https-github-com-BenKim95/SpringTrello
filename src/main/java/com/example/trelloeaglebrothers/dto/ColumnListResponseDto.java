package com.example.trelloeaglebrothers.dto;

import com.example.trelloeaglebrothers.entity.Card;
import com.example.trelloeaglebrothers.entity.ColumnList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ColumnListResponseDto {

    private Long id;
    private String msg;
    private String title;
    private List<CardResponseDto> cardLists = new ArrayList<>();

    public ColumnListResponseDto(String msg) {
        this.msg = msg;
    }


    public ColumnListResponseDto(ColumnList columnList) {
        this.title = columnList.getTitle();
        this.id = columnList.getId();
        this.cardLists = columnList.getCards().stream()
                .map(CardResponseDto::new)
                .sorted(Comparator.comparing(CardResponseDto::getCreatedAt))
                .toList();
    }
}

