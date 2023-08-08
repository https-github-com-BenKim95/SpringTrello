package com.example.trelloeaglebrothers.repository;

import com.example.trelloeaglebrothers.entity.ColumnList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColumnListRepository extends JpaRepository<ColumnList, Long> {
    List<ColumnList> findAllByOrderByOrderNumAsc();
}
