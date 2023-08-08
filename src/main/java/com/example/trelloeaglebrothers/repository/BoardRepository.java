package com.example.trelloeaglebrothers.repository;

import com.example.trelloeaglebrothers.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
