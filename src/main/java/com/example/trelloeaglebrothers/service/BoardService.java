package com.example.trelloeaglebrothers.service;

import com.example.trelloeaglebrothers.dto.BoardRequestDto;
import com.example.trelloeaglebrothers.dto.BoardResponseDto;
import com.example.trelloeaglebrothers.dto.CollaboratorRequestDto;
import com.example.trelloeaglebrothers.entity.Board;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.entity.UserBoard;
import com.example.trelloeaglebrothers.entity.UserRoleEnum;
import com.example.trelloeaglebrothers.repository.BoardRepository;
import com.example.trelloeaglebrothers.repository.UserBoardRepository;
import com.example.trelloeaglebrothers.repository.UserRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;
    private final MessageSource messageSource;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards() {
        return boardRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(BoardResponseDto::new)
                .toList();
    }

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        UserRoleEnum role = UserRoleEnum.MANAGER;
        Board board = boardRepository.save(new Board(requestDto, user));
        // 매니저 권한 부여


        UserBoard userBoard = new UserBoard(user, board, role);

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
//        UserRoleEnum userRoleEnum = user.getRole();
//        if (userRoleEnum == UserRoleEnum.MEMBER && !Objects.equals(board.getAuthor().getId(), user.getId())) {
        if (!Objects.equals(board.getAuthor().getId(), user.getId())) {
            throw new IllegalArgumentException(messageSource.getMessage(
                    "not.your.post",
                    null,
                    "작성자만 수정 및 삭제가 가능합니다",
                    Locale.getDefault()
            ));
        }
    }

//    @Transactional
//    public void addCollaborator(Board board, User collaborator) {
//        if (board.getUserBoards().stream().anyMatch(boardUser -> boardUser.getCollaborator().equals(collaborator))) {
//            throw new IllegalArgumentException("칸반 보드에 이미 협업자로 등록된 사용자입니다.");
//        }
//
//        UserBoard userBoard = new UserBoard(collaborator, board);
//        board.getUserBoards().add(userBoard);
//    }


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

//    @Transactional
//    public ResponseEntity<Message> addCollaborator(Long boardId, CollaboratorRequestDto collaboratorRequestDto, User user) {
//
//        //매니저 체크
//        Optional<UserBoard> checkManager = userBoardRepository.findUserBoardByCollaborator_Id(user.getId());
//        if (checkManager.isEmpty() || !checkManager.get().getRole().equals(UserRoleEnum.MANAGER)) {
//            throw new IllegalArgumentException("매니저 권한이 아닙니다.");
//        }
//
//
//            //보드에 회원 유무 확인
//            Optional<UserBoard> userBoard = userBoardRepository.findUserBoardByCollaborator_Id(collaboratorRequestDto.getId());
//            if (userBoard.get().getRole() == null) {
//                // 회원 초대
//                userBoard.get().setRole(UserRoleEnum.MEMBER);
//                userBoardRepository.save(userBoard.get());
//            } else {
//                    return ResponseEntity.badRequest().body(new Message("초대 불가", 400));
//                }
//        return ResponseEntity.ok().body(new Message("멤버으로 초대 성공", 200));
//    }

    @Transactional
    public ResponseEntity<Message> addCollaborator(Long boardId, CollaboratorRequestDto collaboratorRequestDto, User user) {
        // 매니저 권한 체크
        UserBoard managerUserBoard = userBoardRepository.findUserBoardByCollaborator_Id(collaboratorRequestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("매니저 권한이 아닙니다."));

        // 보드 조회 및 협업자 정보 확인
        Board board = findBoard(boardId);
        User collaborator = userRepository.findById(collaboratorRequestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        Optional<UserBoard> userBoard = userBoardRepository.findUserBoardByCollaborator_Id(collaborator.getId());


        // 이미 초대된 사용자인지 확인
        if (userBoard.isPresent() && userBoard.get().getRole() != null) {
            return ResponseEntity.badRequest().body(new Message("이미 초대된 사용자입니다.", 400));
        }

        // 초대 처리
        if (userBoard.isPresent()) {
            // 이미 회원인 경우 권한 변경
            userBoard.get().setRole(UserRoleEnum.MEMBER);
        } else {
            // 초대되지 않은 사용자인 경우 추가
            UserBoard newUserBoard = new UserBoard(collaborator, board, UserRoleEnum.MEMBER);
            userBoardRepository.save(newUserBoard);
        }

        return ResponseEntity.ok().body(new Message("멤버로 초대 성공", 200));
    }

}

//  for(int i = 0; i <= collaborator.getBoards().size(); i++) {
//          if (collaborator.getBoards().get(i).equals(boardId)) {
//          throw new IllegalArgumentException("해당 회원 가입 할 수 없습니다");
//          }
//          }

