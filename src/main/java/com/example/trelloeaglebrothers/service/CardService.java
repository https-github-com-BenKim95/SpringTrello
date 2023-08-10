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
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@RequiredArgsConstructor
@Service
public class CardService {

    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;
    private final ColumnListRepository columnListRepository;
    private final UserBoardRepository userBoardRepository;
    private final UserCardRepository userCardRepository;
    private final CardCommentRepository cardCommentRepository;
    private final UserRepository userRepository;

    //카드 생성
    @Transactional
    public ResponseEntity<ApiResponseDto> createCard(Long boardId, Long columnListId, CardRequestDto cardRequestDto, User user) {
        checkRole(boardId, user);
        Optional<ColumnList> columnList = columnListRepository.findByBoardIdAndId(boardId, columnListId);
        if (columnList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 컬럼리스트는 존재하지 않습니다."));
        }

        // cardList가 생성될 때 마다 orDerNum이 1씩 증가
        // 조회했을때 null이면 1을 넣고
        // 값이 있으면 그 값에 +1을 한다.
        List<Card> cardList = cardRepository.findAllByOrderByOrderNumAsc();
        Long newOrderNum = cardList.isEmpty() ? 1 : cardList.get(cardList.size() - 1).getOrderNum() + 1;

        Card card = new Card(cardRequestDto, newOrderNum, null, columnList.get());
        cardRepository.save(card);
//        userCardRepository.save(new UserCard(user, card)); -> 안쓸 확률 높음
        return ResponseEntity.status(201).body(new ApiResponseDto(HttpStatus.CREATED, "카드가 작성되었습니다."));
    }

    //카드 수정
    @Transactional
    public CardResponseDto editCard(Long boardId, Long columnListId, Long cardId, CardRequestDto cardRequestDto, User user) {
        checkRole(boardId, user);
        Optional<ColumnList> columnList = columnListRepository.findByBoardIdAndId(boardId, columnListId);
        Optional<Card> card = cardRepository.findById(cardId);

        if (columnList.isEmpty()) {
            throw new IllegalArgumentException("해당 컬럼리스트는 존재하지 않습니다.");
        } else if (card.isEmpty()) {
            throw new IllegalArgumentException("해당 카드는 존재하지 않습니다.");
        }
        card.get().update(cardRequestDto, userCardRepository, userRepository, cardId);

        if (cardRequestDto.getDueDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("마감일은 현재 시간보다 이전일 수 없습니다.");
        }
        return new CardResponseDto(card.get());
    }

    //카드 위치 변경
    @Transactional
    public ResponseEntity<ApiResponseDto> orderSwap(Long boardId, Long columnListId, Long forwardOrder, Long backwardOrder, User user) {
        checkRole(boardId, user);
        Optional<ColumnList> columnList = columnListRepository.findByBoardIdAndId(boardId, columnListId);
        Optional<Card> forwardOrderCard = cardRepository.findById(forwardOrder);
        Optional<Card> backwardOrderCard = cardRepository.findById(backwardOrder);

        if (columnList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 컬럼리스트는 존재하지 않습니다."));
        } else if (forwardOrderCard.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 카드는 존재하지 않습니다."));
        } else if (backwardOrderCard.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 카드는 존재하지 않습니다."));
        }

        long flag = forwardOrderCard.get().getOrderNum();
        forwardOrderCard.get().setOrderNum(backwardOrderCard.get().getOrderNum());
        backwardOrderCard.get().setOrderNum(flag);

        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK, "카드 순서가 변경되었습니다."));
    }

    //카드 삭제
    @Transactional
    public ResponseEntity<ApiResponseDto> deleteCard(Long boardId, Long columnListId, Long cardId, User user) {
        checkRole(boardId, user);
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
        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK, "카드가 삭제되었습니다."));
    }

    // 하나의 메서드에서 여러 레포지토리 메서드를 호출하여 데이터를 생성, 수정 또는 삭제하는 경우에 트랜잭션을 사용하여 데이터의 일관성과 무결성을 보장
    // 단일 레포지토리 메서드를 호출하여 데이터를 생성 또는 삭제하는 작업만 수행하므로, 추가적인 트랜잭션 처리가 필요하지 않음
    //카드 댓글 작성
    @Transactional
    public ResponseEntity<ApiResponseDto> createCardComments(Long boardId, Long columnListId, Long cardId, CardCommentRequestDto cardCommentRequestDto, User user) {
        checkRole(boardId, user);
        Optional<ColumnList> columnList = columnListRepository.findByBoardIdAndId(boardId, columnListId);
        Optional<Card> card = cardRepository.findById(cardId);

        if (columnList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 컬럼리스트는 존재하지 않습니다."));
        } else if (card.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 카드는 존재하지 않습니다."));
        }

        cardCommentRepository.save(new CardComment(cardCommentRequestDto, user, card.get()));
        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK, "카드 댓글이 작성되었습니다."));
    }

    //카드 댓글 수정
    @Transactional
    public ResponseEntity<ApiResponseDto> editCardComments(Long boardId, Long columnListId, Long cardId, Long cardCommentId, CardCommentRequestDto cardCommentRequestDto, User user) {
        checkRole(boardId, user);
        String comments = cardCommentRequestDto.getComment();
        Optional<ColumnList> columnList = columnListRepository.findByBoardIdAndId(boardId, columnListId);
        Optional<Card> card = cardRepository.findById(cardId);
        Optional<CardComment> cardComment = cardCommentRepository.findById(cardCommentId);

        if (columnList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 컬럼리스트는 존재하지 않습니다."));
        } else if (card.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 카드는 존재하지 않습니다."));
        } else if (cardComment.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "해당 댓글이 존재하지 않습니다."));
        }

        cardComment.get().setComment(comments);
        cardCommentRepository.save(cardComment.get());
        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK, "카드 댓글이 수정되었습니다."));
    }


    //카드 댓글 삭제
    @Transactional
    public ResponseEntity<ApiResponseDto> deleteCardComments(Long boardId, Long columnListId, Long cardId, Long cardCommentId, User user) {
        checkRole(boardId, user);
        Optional<ColumnList> columnList = columnListRepository.findByBoardIdAndId(boardId, columnListId);
        Optional<Card> card = cardRepository.findById(cardId);
        Optional<CardComment> cardComment = cardCommentRepository.findById(cardCommentId);

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

    public void checkRole(Long boardId, User user) {
        Optional<Board> checkBoard = boardRepository.findById(boardId);
        if (checkBoard.isEmpty()) {
            throw new IllegalArgumentException("해당 보드는 존재하지 않습니다.");
        }
        Optional<UserBoard> checkUserAndBoardId = userBoardRepository.findUserBoardByCollaborator_IdAndBoard(user.getId(), checkBoard.get());
        if (checkUserAndBoardId.isEmpty()) {
            throw new IllegalArgumentException("보드에 초대되지 않은 사용자입니다.");
//        } else if (checkUserAndBoardId.get().getRole().equals(UserRoleEnum.MANAGER) || checkUserAndBoardId.get().getRole().equals(UserRoleEnum.MEMBER)) {
//            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST, "매니저 또는 멤버 권한이 없습니다."));
//        }
        }
    }
}