package com.example.trelloeaglebrothers.dto;

import com.example.trelloeaglebrothers.entity.ColumnList;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

public class ColumnListResponseDto {

    private String msg;

    public ColumnListResponseDto(String msg) {
        this.msg = msg;
    }
}
