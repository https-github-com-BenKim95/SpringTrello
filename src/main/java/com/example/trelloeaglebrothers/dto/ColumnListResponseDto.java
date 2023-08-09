package com.example.trelloeaglebrothers.dto;

import lombok.Getter;

@Getter
public class ColumnListResponseDto {

    private String msg;

    public ColumnListResponseDto(String msg) {
        this.msg = msg;
    }
}
