package com.example.trelloeaglebrothers.dto;

import com.example.trelloeaglebrothers.entity.Board;
import com.example.trelloeaglebrothers.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardResponseDto {

    private Long board_id;

    private String title;

    private List<UserResponseDto> userList = new ArrayList<>();

    private String color;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board){
        this.board_id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsers().toString();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
