package com.example.trelloeaglebrothers.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CardRequestDto {
    private String title;
    private String description;
    private String color;
    private LocalDateTime dueDate;
    private List<String> members;

}
