package com.example.trelloeaglebrothers.dto;

import com.example.trelloeaglebrothers.entity.Board;
import com.example.trelloeaglebrothers.entity.UserBoard;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoardResponseDto {

    private Long board_id;

    private String title;

    private List<String> userList;

    private String color;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<BoardColumnListResponseDto> columList;

    public BoardResponseDto(Board board){
        this.board_id = board.getId();
        this.title = board.getTitle();
        this.userList = userList(board.getUserBoards());
        this.color = board.getColor();
        this.description = board.getDescription();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();

//        for (UserBoard userBoard : board.getUserBoards()) {
//            userList.add(new UserResponseDto(userBoard.getCollaborator())); // Assuming UserBoard has a 'User' field
//        }
        if (board.getColumnLists() != null) { // 추가: 컬럼 리스트가 null이 아닌 경우에만 변환 작업 수행
            this.columList = board.getColumnLists().stream().map(BoardColumnListResponseDto::new).collect(Collectors.toList());
        }
    }

    private List<String> userList(List<UserBoard> userBoardList) {
        List<String> usernames = new ArrayList<>();
        for (UserBoard userBoard : userBoardList) {
            usernames.add(userBoard.getCollaborator().getUsername());
        }
        return usernames;
    }

}
