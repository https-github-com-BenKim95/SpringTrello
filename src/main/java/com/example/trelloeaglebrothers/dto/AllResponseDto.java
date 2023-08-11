package com.example.trelloeaglebrothers.dto;

import com.example.trelloeaglebrothers.entity.Board;
import com.example.trelloeaglebrothers.entity.ColumnList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class AllResponseDto {
    String title;
    private List<ColumnListResponseDto> columnLists;

    public AllResponseDto(Board board, List<ColumnList> columnLists) {
        this.title = board.getTitle();
        this.columnLists = columnLists.stream().map(ColumnListResponseDto::new).collect(Collectors.toList());
    }
}
