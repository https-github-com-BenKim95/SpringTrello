package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.BoardRequestDto;
import com.example.trelloeaglebrothers.dto.BoardResponseDto;
import com.example.trelloeaglebrothers.repository.UserBoardRepository;
import com.example.trelloeaglebrothers.repository.UserRepository;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import com.example.trelloeaglebrothers.service.BoardService;
import com.example.trelloeaglebrothers.status.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UserRepository userRepository;
    private final UserBoardRepository userBoardRepository;

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


}
