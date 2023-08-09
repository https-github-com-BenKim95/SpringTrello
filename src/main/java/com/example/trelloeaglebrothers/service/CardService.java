package com.example.trelloeaglebrothers.service;

import com.example.trelloeaglebrothers.dto.ApiResponseDto;
import com.example.trelloeaglebrothers.dto.CardCommentRequestDto;
import com.example.trelloeaglebrothers.dto.CardRequestDto;
import com.example.trelloeaglebrothers.dto.CardResponseDto;
import com.example.trelloeaglebrothers.entity.*;
import com.example.trelloeaglebrothers.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final ColumnListRepository columnListRepository;
    private final UserCardRepository userCardRepository;
    private final CardCommentRepository cardCommentRepository;
    private final UserRepository userRepository;

    //카드 생성
    @Transactional
    public ResponseEntity<ApiResponseDto> createCard(Long boardId, Long columnListId, CardRequestDto cardRequestDto, User user) {
        String title = cardRequestDto.getTitle();

        Optional<ColumnList> columnList = columnListRepository.findByBoardIdAndId(boardId, columnListId);
        if (columnList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 컬럼리스트는 존재하지 않습니다."));
        }
        Card card = new Card();
        card.setTitle(title);
        card.setColumnList(columnList.get());
        cardRepository.save(card);
        userCardRepository.save(new UserCard(user, card));
        return ResponseEntity.status(201).body(new ApiResponseDto(HttpStatus.CREATED, "카드가 작성되었습니다."));
    }

    //카드 수정
    @Transactional
    public CardResponseDto editCard(Long boardId, Long columnListId, Long cardId, CardRequestDto cardRequestDto, User user) {
        String title = cardRequestDto.getTitle();
        String description = cardRequestDto.getDescription();
        String color = cardRequestDto.getColor();
        LocalDateTime dueDate = cardRequestDto.getDueDate();
        List<String> userList = cardRequestDto.getUserList();

        List<UserCard> userCardList = new ArrayList<>();
        for (String username : userList) {
            List<UserCard> foundUserCards = userCardRepository.findByUserUsername(username);
            userCardList.addAll(foundUserCards);
        }

        Optional<ColumnList> columnList = columnListRepository.findByBoardIdAndId(boardId, columnListId);
        Optional<Card> card = cardRepository.findById(cardId);

        if (columnList.isEmpty()) {
            throw new IllegalArgumentException("해당 컬럼리스트는 존재하지 않습니다.");
        } else if (card.isEmpty()) {
            throw new IllegalArgumentException("해당 카드는 존재하지 않습니다.");
        } else if (dueDate.isBefore(LocalDateTime.now())) { // 현재시간보다 전으로 설정 X
            throw new IllegalArgumentException("마감일은 현재 시간보다 이전일 수 없습니다.");
        }

        card.get().setTitle(title);
        card.get().setDescription(description);
        card.get().setColor(color);
        card.get().setDueDate(dueDate);
        card.get().setUserCardList(userCardList);

        cardRepository.save(card.get());
        userCardRepository.save(new UserCard(user, card.get()));
        return new CardResponseDto(card.get());
    }

    //카드 삭제
    @Transactional
    public ResponseEntity<ApiResponseDto> deleteCard(Long boardId, Long columnListId, Long cardId, User user) {
        Optional<ColumnList> columnList = columnListRepository.findByBoardIdAndId(boardId, columnListId);
        Optional<Card> card = cardRepository.findById(cardId);
        Optional<UserCard> userCard = userCardRepository.findByUserIdAndCardId(user.getId(), cardId);

        if (columnList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 컬럼리스트는 존재하지 않습니다."));
        } else if (card.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 카드는 존재하지 않습니다."));
        } else if (userCard.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 카드는 존재하지 않습니다."));
        }

        cardRepository.delete(card.get());
        userCardRepository.delete(userCard.get());
        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK, "카드가 삭제되었습니다."));
    }

    // 하나의 메서드에서 여러 레포지토리 메서드를 호출하여 데이터를 생성, 수정 또는 삭제하는 경우에 트랜잭션을 사용하여 데이터의 일관성과 무결성을 보장
    // 단일 레포지토리 메서드를 호출하여 데이터를 생성 또는 삭제하는 작업만 수행하므로, 추가적인 트랜잭션 처리가 필요하지 않음
    //카드 댓글 작성
    public ResponseEntity<ApiResponseDto> createCardComments(Long boardId, Long columnListId, Long cardId, CardCommentRequestDto cardCommentRequestDto, User user) {
        Optional<ColumnList> columnList = columnListRepository.findByBoardIdAndId(boardId, columnListId);
        Optional<Card> card = cardRepository.findById(cardId);
        String comments = cardCommentRequestDto.getComments();

        if (columnList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 컬럼리스트는 존재하지 않습니다."));
        } else if (card.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 카드는 존재하지 않습니다."));
        }

        cardCommentRepository.save(new CardComment(comments, user, card.get()));
        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK, "카드 댓글이 작성되었습니다."));
    }

    //카드 댓글 수정
    public ResponseEntity<ApiResponseDto> editCardComments(Long boardId, Long columnListId, Long cardId, Long cardCommentId, CardCommentRequestDto cardCommentRequestDto, User user) {
        String comments = cardCommentRequestDto.getComments();
        Optional<ColumnList> columnList = columnListRepository.findByBoardIdAndId(boardId, columnListId);
        Optional<Card> card = cardRepository.findById(cardId);
        Optional<CardComment> cardComment = cardCommentRepository.findByIdAndUserId(cardCommentId, user.getId());

        if (columnList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 컬럼리스트는 존재하지 않습니다."));
        } else if (card.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 카드는 존재하지 않습니다."));
        }else if (cardComment.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 댓글이 존재하지 않습니다."));
        }

        cardComment.get().setComments(comments);
        cardCommentRepository.save(cardComment.get());
        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK, "카드 댓글이 수정되었습니다."));
    }


    //카드 댓글 삭제
    public ResponseEntity<ApiResponseDto> deleteCardComments(Long boardId, Long columnListId, Long cardId, Long cardCommentId, User user) {
        Optional<ColumnList> columnList = columnListRepository.findByBoardIdAndId(boardId, columnListId);
        Optional<Card> card = cardRepository.findById(cardId);
        Optional<CardComment> cardComment = cardCommentRepository.findByIdAndUserId(cardCommentId, user.getId());

        if (columnList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 컬럼리스트는 존재하지 않습니다."));
        } else if (card.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 카드는 존재하지 않습니다."));
        } else if (cardComment.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 댓글은 존재하지 않습니다."));
        }

        cardCommentRepository.delete(cardComment.get());
        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK, "카드 댓글이 삭제되었습니다."));
    }

}