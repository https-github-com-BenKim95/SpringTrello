package com.example.trelloeaglebrothers.dto;

import com.example.trelloeaglebrothers.entity.ColumnList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardColumnListResponseDto {
    private Long columnList_id;
    private String title;
    private Long orderNum;


    public BoardColumnListResponseDto (ColumnList columnList) {
        this.columnList_id = columnList.getId();
        this.title = columnList.getTitle();
        this.orderNum = columnList.getOrderNum();
    }
}
