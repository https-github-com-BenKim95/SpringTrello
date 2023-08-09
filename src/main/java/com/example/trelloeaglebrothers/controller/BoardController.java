package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.BoardRequestDto;
import com.example.trelloeaglebrothers.dto.BoardResponseDto;
import com.example.trelloeaglebrothers.dto.CollaboratorRequestDto;
import com.example.trelloeaglebrothers.entity.Board;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.entity.UserBoard;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import com.example.trelloeaglebrothers.service.BoardService;
import com.example.trelloeaglebrothers.service.UserService;
import com.example.trelloeaglebrothers.status.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @GetMapping("/board")
    public List<BoardResponseDto> getBoards() {
        return boardService.getBoards();
    }

    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

    @PutMapping("/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return boardService.updateBoard(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<Message> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return boardService.deleteBoard(id, userDetails.getUser());
    }

    @PostMapping("/board/collaborator/{boardId}")
    public ResponseEntity<Message> addCollaborator(@PathVariable Long boardId,  @RequestBody CollaboratorRequestDto collaboratorRequestDto) {
        Board board = boardService.findBoard(boardId);

        try {
            User collaborator = userService.findById(collaboratorRequestDto.getId());
            UserBoard boardUser = boardService.findCollaborator(collaborator.getId());
            boardService.addCollaborator(board, collaborator);

            if (collaborator.equals(board.getAuthor())) {
                return ResponseEntity.badRequest().body(new Message("입력하신 아이디는 칸반 보드의 오너입니다.", HttpStatus.BAD_REQUEST.value()));
            }

            if (boardUser.getCollaborator().getId().equals(collaborator.getId())) {
                return ResponseEntity.badRequest().body(new Message("해당 칸반 보드에 이미 등록된 협업자입니다.", HttpStatus.BAD_REQUEST.value()));
            }

        } catch (IllegalArgumentException e) {
            log.error("\nERROR : add Collaborator to the board");
            return ResponseEntity.badRequest()
                    .body(new Message(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok().body(new Message("칸반 보드에 협업자가 등록되었습니다.", HttpStatus.OK.value()));
    }

}
