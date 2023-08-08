package com.example.trelloeaglebrothers.repository;

import com.example.trelloeaglebrothers.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
