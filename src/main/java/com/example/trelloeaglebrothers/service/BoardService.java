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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards() {
        return boardRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(BoardResponseDto::new)
                .toList();
    }

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {

        Board board = boardRepository.save(new Board(requestDto, user));

        // 매니저 권한 부여
        user.setRole(UserRoleEnum.MANAGER);

        userRepository.save(user);

        UserBoard userBoard = new UserBoard(user, board);


        userBoardRepository.save(userBoard);

        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto, User user) {
        Board board = confirmBoard(boardId);
        confirmUser(board, user);
        board.update(requestDto); // 게시글 정보 업데이트만 수행

        return new BoardResponseDto(board);
    }


    @Transactional
    public ResponseEntity<Message> deleteBoard(Long boardId, User user) {
        Board board = confirmBoard(boardId);
        confirmUser(board, user);

        boardRepository.delete(board);
        String msg = "삭제 완료";
        Message message = new Message(msg, HttpStatus.OK.value());

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 공통 로직
    public Board confirmBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new IllegalArgumentException(messageSource.getMessage(
                                "not.exist.post",
                                null,
                                "해당 게시물이 존재하지 않습니다",
                                Locale.getDefault()
                        ))
                );
    }

    private void confirmUser(Board board, User user) {
        if (!Objects.equals(board.getAuthor().getId(), user.getId())) { // 보드 값 받아오고 있는 부분 수정
            throw new IllegalArgumentException(messageSource.getMessage(
                    "not.your.post",
                    null,
                    "작성자만 수정 및 삭제가 가능합니다",
                    Locale.getDefault()
            ));
        }
    }

    @Transactional
    public void addCollaborator(Board board, User collaborator) {
        if (board.getUserBoards().stream().anyMatch(boardUser -> boardUser.getCollaborator().equals(collaborator))) {
            throw new IllegalArgumentException("칸반 보드에 이미 협업자로 등록된 사용자입니다.");
        }

        UserBoard userBoard = new UserBoard(collaborator, board);
        board.getUserBoards().add(userBoard);
    }

//    @Transactional
//    public void updateCollaborator(UserBoard boardUser, User newCollaborator) {
//        if (boardUser.getBoard().getUserBoards().stream().anyMatch(user -> user.getCollaborator().equals(newCollaborator))) {
//            throw new IllegalArgumentException("이미 협업자로 할당된 사용자입니다.");
//        }
//
//        boardUser.updateCollaborator(newCollaborator);
//    }


    public Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 칸반 보드입니다."));
    }

    public UserBoard findCollaborator(Long id) {
        return userBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 칸반 보드에 존재하지 않는 협업자입니다."));
    }
}

