package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.AllResponseDto;
import com.example.trelloeaglebrothers.dto.BoardResponseDto;
import com.example.trelloeaglebrothers.dto.ColumnListRequestDto;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import com.example.trelloeaglebrothers.service.BoardService;
import com.example.trelloeaglebrothers.service.ColumnListService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ColumnsViewController {

    private final ColumnListService columnListService;
    private final BoardService boardService;


//    @GetMapping("/board/{board_id}/column/column_list/{column_list_id}") // id 값 지워야함
//    public String getColumns(@PathVariable Long board_id,
//                             @PathVariable Long column_list_id,
//                             @AuthenticationPrincipal UserDetailsImpl userDetails,
//                             Model model) {
//
//        model.addAttribute("columns", columnListService.getColumn(userDetails.getUser(), board_id, column_list_id));
//
//
//        return "columns";
//
//
//    }

    //보드를 클릭하면 여기로 들어와서 board_id의 값을 get으로 보여준다.(생성되었던 칼럼 리스트들이 보여야 한다.)
    //다음 columns에는 컬럼 생성 버튼이 있고
    //컬럼을 생성하면 columns에서 즉 보드 화면에 생성한 컬럼이 생긴다.
    //위 과정에서 생성된 컬럼이 get으로 조회가 되고 화면에 보인다.

    @GetMapping("/board/{board_id}")
    public String getBoard(@PathVariable Long board_id,
                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                           Model model) {

        //보드 화면
        AllResponseDto allResponseDto = boardService.getBoard(board_id);
        model.addAttribute("columns", allResponseDto);

        return "columns";


    }

    //칼럼 이름 수정
    @GetMapping("/board/column_list")
    public String modifiedColumnList2(@RequestParam("boardId") Long boardId,
                                      @RequestParam("id") Long column_list_id,
                                      @RequestParam("title") String title,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ColumnListRequestDto requestDto = new ColumnListRequestDto();
        requestDto.setTitle(title);
        columnListService.modifiedColumnList(boardId, column_list_id, userDetails.getUser(), requestDto);
        return "redirect:/api/board/" + boardId;
    }


}
