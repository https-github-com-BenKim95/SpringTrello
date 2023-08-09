package com.example.trelloeaglebrothers.repository;

import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {
}