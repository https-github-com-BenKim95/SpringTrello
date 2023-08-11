package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.ApiResponseDto;
import com.example.trelloeaglebrothers.dto.CardCommentRequestDto;
import com.example.trelloeaglebrothers.dto.CardRequestDto;
import com.example.trelloeaglebrothers.dto.CardResponseDto;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import com.example.trelloeaglebrothers.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class CardController {

    private final CardService cardService;

    //카드 작성
    @PostMapping("/board/{board_id}/column_list/{column_list_id}/card")
    public ResponseEntity<ApiResponseDto> createCard(@PathVariable Long board_id, @PathVariable Long column_list_id,
                                                     @RequestBody CardRequestDto cardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.createCard(board_id, column_list_id, cardRequestDto, userDetails.getUser());
    }

    // 카드 수정
    @PutMapping("/board/{board_id}/column_list/{column_list_id}/card/{card_id}")
    public ResponseEntity<CardResponseDto> editCard(@PathVariable Long board_id, @PathVariable Long column_list_id, @PathVariable Long card_id,
                                                   @RequestBody CardRequestDto cardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.editCard(board_id, column_list_id, card_id, cardRequestDto, userDetails.getUser()));
    }

    // 카드 위치 변경
    @PutMapping("/board/{board_id}/column_list/{column_list_id}/card/{forward_order}/{backward_order}")
    public ResponseEntity<ApiResponseDto> orderSwap(@PathVariable Long board_id, @PathVariable Long column_list_id, @PathVariable Long forward_order, @PathVariable Long backward_order,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.orderSwap(board_id, column_list_id, forward_order, backward_order, userDetails.getUser());
    }

    // 카드 삭제
    @DeleteMapping("/board/{board_id}/column_list/{column_list_id}/card/{card_id}")
    public ResponseEntity<ApiResponseDto> deleteCard(@PathVariable Long board_id, @PathVariable Long column_list_id, @PathVariable Long card_id,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.deleteCard(board_id, column_list_id, card_id, userDetails.getUser());
    }

    // 카드 댓글 작성
    @PostMapping("/board/{board_id}/column_list/{column_list_id}/card/{card_id}/comment")
    public ResponseEntity<ApiResponseDto> createCardComments(@PathVariable Long board_id, @PathVariable Long column_list_id, @PathVariable Long card_id,
                                                             @RequestBody CardCommentRequestDto cardCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.createCardComments(board_id, column_list_id, card_id, cardCommentRequestDto, userDetails.getUser());
    }

    // 카드 댓글 수정
    @PutMapping("/board/{board_id}/column_list/{column_list_id}/card/{card_id}/comment/{card_comment_id}")
    public ResponseEntity<ApiResponseDto> editCardComments(@PathVariable Long board_id, @PathVariable Long column_list_id, @PathVariable Long card_id, @PathVariable Long card_comment_id,
                                                           @RequestBody CardCommentRequestDto cardCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.editCardComments(board_id, column_list_id, card_id, card_comment_id, cardCommentRequestDto, userDetails.getUser());
    }

    // 카드 댓글 삭제
    @DeleteMapping("/board/{board_id}/column_list/{column_list_id}/card/{card_id}/comment/{card_comment_id}")
    public ResponseEntity<ApiResponseDto> createCardComments(@PathVariable Long board_id, @PathVariable Long column_list_id, @PathVariable Long card_id, @PathVariable Long card_comment_id,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.deleteCardComments(board_id, column_list_id, card_id, card_comment_id, userDetails.getUser());
    }


}