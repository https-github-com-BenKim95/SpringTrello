package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.CardResponseDto;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import com.example.trelloeaglebrothers.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CardViewController {

    private final CardService cardService;

    //카드 조회
    @GetMapping("/board/column_list/card_show")
    public String getCard(@RequestParam Long boardId, @RequestParam Long columnId, @RequestParam Long cardId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto cardResponseDto = cardService.getCard(boardId,columnId,cardId, userDetails.getUser());
        model.addAttribute("boardId",boardId);
        model.addAttribute("columnId",columnId);
        model.addAttribute("card", cardResponseDto);
        return "editCard";
    }
}
