package com.example.trelloeaglebrothers.service;

import com.example.trelloeaglebrothers.dto.BoardRequestDto;
import com.example.trelloeaglebrothers.dto.BoardResponseDto;
import com.example.trelloeaglebrothers.entity.Board;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.entity.UserBoard;
import com.example.trelloeaglebrothers.entity.UserRoleEnum;
import com.example.trelloeaglebrothers.repository.BoardRepository;
import com.example.trelloeaglebrothers.repository.UserBoardRepository;
import com.example.trelloeaglebrothers.status.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MessageSource messageSource;
    private final UserBoardRepository userBoardRepository;

    public List<BoardResponseDto> getBoards() {
        return boardRepository.findAllByOrderByCreatedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = boardRepository.save(new Board(requestDto, user));
        UserBoard userBoard = new UserBoard(user, board);
        userBoardRepository.save(userBoard);

        return new BoardResponseDto(board);
    }


    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto, User user) {
        Board board = findBoard(boardId);
        confirmUser(board, user);
        board.update(requestDto); // 게시글 정보 업데이트만 수행

        return new BoardResponseDto(board);
    }

    public ResponseEntity<Message> deleteBoard(Long boardId, User user){
        Board board = findBoard(boardId);
        confirmUser(board, user);

        boardRepository.delete(board);
        String msg ="삭제 완료";
        Message message = new Message(msg, HttpStatus.OK.value());

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 공통 로직
    private Board findBoard(Long boardId){
        return boardRepository.findById(boardId).orElseThrow(()->
                new IllegalArgumentException(messageSource.getMessage(
                        "not.exist.post",
                        null,
                        "해당 게시물이 존재하지 않습니다",
                        Locale.getDefault()
                ))
        );
    }

    private void confirmUser(Board board, User user) {
        UserRoleEnum userRoleEnum = user.getRole();
        if (userRoleEnum == UserRoleEnum.MEMBER && !Objects.equals(board.getUserBoards(), user.getId())) {
            throw new IllegalArgumentException(messageSource.getMessage(
                    "not.your.post",
                    null,
                    "작성자만 수정 및 삭제가 가능합니다",
                    Locale.getDefault()
            ));
        }
    }

}
