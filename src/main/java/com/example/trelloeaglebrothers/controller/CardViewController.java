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
@RequestMapping("/api")
public class CardViewController {

    private final CardService cardService;

    //카드 조회
    @GetMapping("/board/{board_id}/column_list/{column_list_id}/card/{card_id}")
    public String getCard(@PathVariable Long board_id, @PathVariable Long column_list_id, @PathVariable Long card_id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto cardResponseDto = cardService.getCard(board_id,column_list_id,card_id, userDetails.getUser());
        model.addAttribute("card", cardResponseDto);
        return "showCards";
    }
}
