package com.example.trelloeaglebrothers.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CardRequestDto {
    private String title;
    private String description;
    private String color;
    private LocalDateTime dueDate;
    private List<String> userList;
}
