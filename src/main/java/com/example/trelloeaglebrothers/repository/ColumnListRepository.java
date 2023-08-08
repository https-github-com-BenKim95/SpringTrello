package com.example.trelloeaglebrothers.repository;

import com.example.trelloeaglebrothers.entity.ColumnList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColumnListRepository extends JpaRepository<ColumnList, Long> {
    Optional<ColumnList> findByBoardIdAndId (Long BoardId, Long columnListId);
}
