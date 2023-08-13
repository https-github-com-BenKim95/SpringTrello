package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.ColumnListRequestDto;
import com.example.trelloeaglebrothers.dto.ColumnListResponseDto;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import com.example.trelloeaglebrothers.service.ColumnListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ColumListController {




//    //칼럼 조회

    @GetMapping("/board/{board_id}/column/column_list/{column_list_id}")
    public ColumnListResponseDto getColumns(@PathVariable Long board_id,
                                            @PathVariable Long column_list_id,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        return columnListService.getColumn(userDetails.getUser(), board_id, column_list_id);


    }

    //칼럼 생성
    //보드 내부에 컬럼을 생성할 수 있어야 한다
    // 컬림에는 ex) backlong in progress done
    private final ColumnListService columnListService;


    @PostMapping("/board/{board_id}/column_list")
    public ResponseEntity<ColumnListResponseDto> createColumnList(@PathVariable Long board_id,
                                                                  @RequestBody ColumnListRequestDto requestDto,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        try {
            columnListService.createColumnList(board_id, userDetails.getUser(), requestDto);
            return ResponseEntity.ok().body(new ColumnListResponseDto("칼럼 생성 완료"));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ColumnListResponseDto("컬럼 생성에 실패했습니다."));

        }


    }

//    //칼럼 이름 수정
//    @PutMapping("/board/{board_id}/column_list/{column_list_id}")
//    public ColumnListResponseDto modifiedColumnList(@PathVariable Long board_id,
//                                                    @PathVariable Long column_list_id,
//                                                    @AuthenticationPrincipal UserDetailsImpl userDetails,
//                                                    @RequestBody ColumnListRequestDto requestDto) {
//        log.info("칼럼 이름 수정", board_id);
//        log.info("칼럼 이름 수정", column_list_id);
//        return columnListService.modifiedColumnList(board_id, column_list_id, userDetails.getUser(), requestDto);
//
//
//    }

    //칼럼 삭제
    @DeleteMapping("/board/{board_id}/column_list/{column_list_id}")
    public ResponseEntity<ColumnListResponseDto> deleteColumnList(@PathVariable Long board_id,
                                                                  @PathVariable Long column_list_id,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {


        try {
            columnListService.deleteColumnList(board_id, column_list_id, userDetails.getUser());
            return ResponseEntity.ok().body(new ColumnListResponseDto("칼럼 삭제되었습니다"));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ColumnListResponseDto("칼럼 삭제에 실패했습니다."));


        }

    }

    //칼럼 순서 이동
    // 두 칼럼의  idr값을 받아온다
    // 두 칼럼의 orderNum을 swap 한다.

    @PutMapping("/board/{board_id}/column_list/{forward_order}/{backward_order}")
    public ColumnListResponseDto orderSwap(@PathVariable Long board_id,
                                           @PathVariable Long forward_order,
                                           @PathVariable Long backward_order,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return columnListService.orderSwap(board_id, userDetails.getUser(), forward_order, backward_order);

    }

}
