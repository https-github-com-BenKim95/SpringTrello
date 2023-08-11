package com.example.trelloeaglebrothers.repository;

import com.example.trelloeaglebrothers.entity.ColumnList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import java.util.Optional;

public interface ColumnListRepository extends JpaRepository<ColumnList, Long> {

    List<ColumnList> findAllByOrderByOrderNumAsc();
    Optional<ColumnList> findByBoardIdAndId (Long BoardId, Long columnListId);

    Optional<ColumnList> findColumnListByBoard_IdAndId(Long BoardId, Long columnListId);

    List<ColumnList> findByBoard_Id(Long BoardId);


}
