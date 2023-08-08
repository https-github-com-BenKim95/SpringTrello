package com.example.trelloeaglebrothers.service;

import com.example.trelloeaglebrothers.dto.ColumnListRequestDto;
import com.example.trelloeaglebrothers.dto.ColumnListResponseDto;
import com.example.trelloeaglebrothers.entity.Board;
import com.example.trelloeaglebrothers.entity.ColumnList;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.entity.UserRoleEnum;
import com.example.trelloeaglebrothers.repository.BoardRepository;
import com.example.trelloeaglebrothers.repository.ColumnListRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.RejectedExecutionException;

@Service
@NoArgsConstructor
public class ColumnListService {
    BoardRepository boardRepository;
    ColumnListRepository columnListRepository;

    //칼럼생성
    public void createColumnList(Long boardId, ColumnListRequestDto requestDto, User user) {

        //보드가 존재 하는지 체크
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드가 존재하지 않습니다."));

        //보드 멤버인지 확인
//        User memberCheck = null;
//        if (user.getRole().equals(UserRoleEnum.MANAGER) || user.getRole().equals(UserRoleEnum.MEMBER)) {

            //보드가 존재 한다면 칼럼 생성
            ColumnList columnList = new ColumnList(board, requestDto);

            //저장
            columnListRepository.save(columnList);

//        } else throw new RejectedExecutionException("접근 권한이 없습니다.");


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
        User memberCheck = null;
        if (user.getRole().equals(UserRoleEnum.MANAGER) || user.getRole().equals(UserRoleEnum.MEMBER)) {


            columnList.update(requestDto.getTitle(), board);
            return new ColumnListResponseDto("컬럼이 변경되었습니다.");

        } else throw new RejectedExecutionException("접근 권한이 없습니다.");
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
        User memberCheck = null;
        if (user.getRole().equals(UserRoleEnum.MANAGER) || user.getRole().equals(UserRoleEnum.MEMBER)) {

            columnListRepository.delete(columnList);

        } else throw new RejectedExecutionException("삭제 권한이 없습니다.");


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

        columnList1.setOrderNum(columnList2.getOrderNum());
        columnList2.setOrderNum(columnList1.getOrderNum());

        return new ColumnListResponseDto("칼럼 순서가 변경되었습니다.");

    }
}
