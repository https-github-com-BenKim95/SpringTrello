package com.example.trelloeaglebrothers.controller;

import com.example.trelloeaglebrothers.dto.ColumnListResponseDto;
import com.example.trelloeaglebrothers.security.UserDetailsImpl;
import com.example.trelloeaglebrothers.service.ColumnListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ColumnsViewController {

    private final ColumnListService columnListService;

    @GetMapping("/board/{board_id}/column/column_list/{column_list_id}")
    public ColumnListResponseDto getColumns(@PathVariable Long board_id,
                                            @PathVariable Long column_list_id,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){

        return columnListService.getColumn(userDetails.getUser(), board_id, column_list_id);
    }
}