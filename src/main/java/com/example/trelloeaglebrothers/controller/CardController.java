package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.ApiResponseDto;
import com.example.trelloeaglebrothers.dto.CardRequestDto;
import com.example.trelloeaglebrothers.entity.Card;
import com.example.trelloeaglebrothers.service.CardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class CardController {

    private final CardService cardService;

    @Transactional
    @PostMapping("/api/board/{board_id}/column_list/{column_list_id}/card")
    public ResponseEntity<ApiResponseDto> createCard (@PathVariable Long board_id, @PathVariable Long column_list_id, @RequestBody CardRequestDto cardRequestDto) {
        return cardService.createCard(board_id, column_list_id, cardRequestDto);
    }
}
