package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.*;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import com.example.trelloeaglebrothers.service.CardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class CardController {

    private final CardService cardService;

    //카드 작성
    @PostMapping("/api/board/{board_id}/column_list/{column_list_id}/card")
    public ResponseEntity<ApiResponseDto> createCard(@PathVariable Long board_id, @PathVariable Long column_list_id,
                                                     @RequestBody CardRequestDto cardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.createCard(board_id, column_list_id, cardRequestDto, userDetails.getUser());
    }

    // 카드 수정
    @PutMapping("/api/board/{board_id}/column_list/{column_list_id}/card/{card_id}")
    public ResponseEntity<ApiResponseDto> editCard(@PathVariable Long board_id, @PathVariable Long column_list_id, @PathVariable Long card_id,
                                                   @RequestBody CardRequestDto cardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.editCard(board_id, column_list_id, card_id, cardRequestDto, userDetails.getUser());
    }

    // 카드 위치 옮기기


    // 카드 삭제
    @DeleteMapping("/api/board/{board_id}/column_list/{column_list_id}/card/{card_id}")
    public ResponseEntity<ApiResponseDto> deleteCard(@PathVariable Long board_id, @PathVariable Long column_list_id, @PathVariable Long card_id,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.deleteCard(board_id, column_list_id, card_id, userDetails.getUser());
    }

    // 카드 댓글 작성
    @PostMapping("/api/board/{board_id}/column_list/{column_list_id}/card{card_id}/comment")
    public ResponseEntity<ApiResponseDto> createCardComments(@PathVariable Long board_id, @PathVariable Long column_list_id, @PathVariable Long card_id,
                                                             @RequestBody CardCommentRequestDto cardCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.createCardComments(board_id, column_list_id, card_id, cardCommentRequestDto, userDetails.getUser());
    }

    // 카드 댓글 수정
    @PutMapping("/api/board/{board_id}/column_list/{column_list_id}/card{card_id}/comment{card_comment_id}")
    public ResponseEntity<ApiResponseDto> editCardComments(@PathVariable Long board_id, @PathVariable Long column_list_id, @PathVariable Long card_id, @PathVariable Long card_comment_id,
                                                           @RequestBody CardCommentRequestDto cardCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.editCardComments(board_id, column_list_id, card_id, card_comment_id, cardCommentRequestDto, userDetails.getUser());
    }

    // 카드 댓글 삭제
    @DeleteMapping("/api/board/{board_id}/column_list/{column_list_id}/card{card_id}/comment{card_comment_id}")
    public ResponseEntity<ApiResponseDto> createCardComments(@PathVariable Long board_id, @PathVariable Long column_list_id, @PathVariable Long card_id, @PathVariable Long card_comment_id,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.deleteCardComments(board_id, column_list_id, card_id, card_comment_id, userDetails.getUser());
    }


}