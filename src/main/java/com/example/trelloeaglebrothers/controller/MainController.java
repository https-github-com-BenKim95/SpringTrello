package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.BoardResponseDto;
import com.example.trelloeaglebrothers.entity.Board;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.repository.BoardRepository;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import com.example.trelloeaglebrothers.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    // 메인페이지
    @GetMapping("/")
    public String Home(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            model.addAttribute("nickName", user.getNickName());
        }
        return "index";
    }

    @GetMapping("/card")
    public String card(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            model.addAttribute("nickName", user.getNickName());
        }
        return "card";
    }

    @GetMapping("/newBoard")
    public String newBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam(required = false) Long id, Model model) {
        return handleBoardPage(userDetails, id, model, "새 게시글");
    }

    @GetMapping("/editBoard")
    public String editBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        return handleBoardPage(userDetails, null, model, "수정");
    }

    private String handleBoardPage(UserDetailsImpl userDetails, Long id, Model model, String logMessage) {
        if (userDetails != null) {
            if (id == null) {
                log.info(logMessage);
                model.addAttribute("board", new BoardResponseDto());
            } else {
                log.info(logMessage);
                Board board = boardService.findBoard(id); // 수정된 부분
                model.addAttribute("board", new BoardResponseDto(board));
            }
        }
        return "newBoard";
    }

    @GetMapping("/memberMain")
    public String memberMain(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        List<BoardResponseDto> boardResponseDtos = boardService.getBoards();
        model.addAttribute("boards", boardResponseDtos);

        return "memberMain";
    }

    @GetMapping("/inviteMember")
    public String inviteMember(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails != null) {
            // 필요한 로직 및 데이터 처리
            // 예: User user = userDetails.getUser();
            // model.addAttribute("user", user);
        }
        return "invitemember";
    }

}