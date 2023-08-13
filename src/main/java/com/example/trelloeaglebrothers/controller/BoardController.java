package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.BoardRequestDto;
import com.example.trelloeaglebrothers.dto.BoardResponseDto;
import com.example.trelloeaglebrothers.dto.CollaboratorRequestDto;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import com.example.trelloeaglebrothers.service.BoardService;
import com.example.trelloeaglebrothers.status.Message;
import jakarta.validation.Valid;
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


    //보드 생성
    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody @Valid BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

    //보드 수정
    @PutMapping("/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails.getUser());
    }

    //보드 삭제
    @DeleteMapping("/board/{id}")
    public ResponseEntity<Message> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        try {
            boardService.deleteBoard(id, user);
            String msg = "보드 삭제 완료";
            return ResponseEntity.ok(new Message(msg, HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            String errorMsg = "보드 삭제 실패";
            return ResponseEntity.badRequest().body(new Message(errorMsg, HttpStatus.BAD_REQUEST.value()));
        }
    }

    //초대
    @PostMapping("/board/collaborator/{boardId}")
    public ResponseEntity<Message> addCollaborator(@PathVariable Long boardId, @RequestBody CollaboratorRequestDto collaboratorRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return boardService.addCollaborator(boardId, collaboratorRequestDto, userDetails.getUser());
    }



    //내 보드만 조회
    @GetMapping("/board")
    public List<BoardResponseDto> getUserBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return boardService.getUserBoards(user);
    }

}
