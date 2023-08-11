package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import com.example.trelloeaglebrothers.service.ColumnListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ColumnsViewController {

    private final ColumnListService columnListService;

    //칼럼 하나가 아님 보드를 조회하면 칼럼들이 나와야 함
    //보드조회
    //조회한 보드에서 칼럼 생성하고
    //칼럼 조회를 만들어야 함


    @GetMapping("/board/{board_id}/column/column_list/{column_list_id}") // id 값 지워야함
    public String getColumns(@PathVariable Long board_id,
                                            @PathVariable Long column_list_id,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                                            Model model) {

        model.addAttribute("columns", columnListService.getColumn(userDetails.getUser(), board_id, column_list_id));


        return "columns";


    }


}
