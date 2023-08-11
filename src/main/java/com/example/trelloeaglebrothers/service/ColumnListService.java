package com.example.trelloeaglebrothers.service;

import com.example.trelloeaglebrothers.dto.CardResponseDto;
import com.example.trelloeaglebrothers.dto.ColumnListRequestDto;
import com.example.trelloeaglebrothers.dto.ColumnListResponseDto;
import com.example.trelloeaglebrothers.entity.*;
import com.example.trelloeaglebrothers.repository.BoardRepository;
import com.example.trelloeaglebrothers.repository.ColumnListRepository;
import com.example.trelloeaglebrothers.repository.UserBoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColumnListService {

    private final BoardRepository boardRepository;

    private final ColumnListRepository columnListRepository;

    private final UserBoardRepository userBoardRepository;


    //칼럼 단건 조회 할 때 카드 내용이 나와야 한다.
    public ColumnListResponseDto getColumn(User user, Long boardId, Long column_list_id){

        //보드가 존재 하는지 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드가 존재하지 않습니다."));


        //보드 멤버인지 확인
        UserBoard userBoard = userBoardRepository.findUserBoardByCollaborator_Id(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("보드 멤버가 아닙니다."));

       ColumnList columnList = columnListRepository.findById(column_list_id)
               .orElseThrow(()-> new IllegalArgumentException("해당 컬럼이 존재하지 않음"));


        return new ColumnListResponseDto(columnList);

    }

    //칼럼생성
    @Transactional
    public void createColumnList(Long boardId, User user, ColumnListRequestDto requestDto) {

        //보드가 존재 하는지 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드가 존재하지 않습니다."));


        //보드 멤버인지 확인
        UserBoard userBoard = userBoardRepository.findUserBoardByCollaborator_Id(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("보드 멤버가 아닙니다."));


        //칼럼 생성
        ColumnList columnList = new ColumnList(board, requestDto);

        // columnList가 생성 될 때 마다 orderNUm +1 씩 증가
        //조회를 했을 때 null 이면 1은 넣고
        //조회를 했을 때 값이 있으면 그 값 플러스 1을 하면 됨

        List<ColumnList> columnLists = columnListRepository.findAllByOrderByOrderNumAsc();
        Long newOrderNum = columnLists.isEmpty() ? 1 : columnLists.get(columnLists.size() - 1).getOrderNum() + 1;
        columnList.setOrderNum(newOrderNum);

        //저장
        columnListRepository.save(columnList);


    }

    //칼럼 이름 수정
    @Transactional
    public ColumnListResponseDto modifiedColumnList(Long boardId,
                                                    Long columnListId,
                                                    User user,
                                                    ColumnListRequestDto requestDto) {

        //보드가 존재 하는지 체크
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드가 존재하지 않습니다."));

        //칼럼이 존재 하는지 체크
        ColumnList columnList = columnListRepository.findById(columnListId)
                .orElseThrow(() -> new IllegalArgumentException("해당 칼럼이 존재하지 않습니다."));

        //보드 멤버인지 확인
        UserBoard userBoard = userBoardRepository.findUserBoardByCollaborator_Id(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("보드 멤버가 아닙니다."));


        columnList.update(requestDto.getTitle(), board);
        return new ColumnListResponseDto("컬럼이 변경되었습니다.");

    }

    //칼럼 삭제

    @Transactional
    public void deleteColumnList(Long boardId, Long columnListId, User user) {
        //보드가 존재 하는지 체크
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드가 존재하지 않습니다."));

        //칼럼이 존재 하는지 체크
        ColumnList columnList = columnListRepository.findById(columnListId)
                .orElseThrow(() -> new IllegalArgumentException("해당 칼럼이 존재하지 않습니다."));

        //보드 멤버인지 확인
        UserBoard userBoard = userBoardRepository.findUserBoardByCollaborator_Id(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("보드 멤버가 아닙니다."));

        columnListRepository.delete(columnList);



    }


    //컬럼 순서 변경
    @Transactional
    public ColumnListResponseDto orderSwap(Long boardId, User user, Long forwardOrder, Long backwardOrder) {
        //보드가 존재 하는지 체크
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드가 존재하지 않습니다."));

        //바꿀 첫 번째 칼럼이 존재 하는지 체크
        ColumnList columnList1 = columnListRepository.findById(forwardOrder)
                .orElseThrow(() -> new IllegalArgumentException("해당 칼럼이 존재하지 않습니다."));
        //바꿀 두 번째 컬럼이 존재하는지 체크
        ColumnList columnList2 = columnListRepository.findById(backwardOrder)
                .orElseThrow(() -> new IllegalArgumentException("해당 칼럼이 존재하지 않습니다."));

        //서로 순서 바꿔서 값 넣어주기
        long flag = columnList1.getOrderNum(); // 첫 번째 받는 값 저장하기
        columnList1.setOrderNum(columnList2.getOrderNum()); // 첫 번째 값 두 번째 값에 넣기
        columnList2.setOrderNum(flag); // 저장했던 flag 값 넣기

        return new ColumnListResponseDto("칼럼 순서가 변경되었습니다.");

    }
}
