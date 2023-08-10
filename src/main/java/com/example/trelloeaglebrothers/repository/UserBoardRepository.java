package com.example.trelloeaglebrothers.repository;

import com.example.trelloeaglebrothers.entity.Board;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.entity.UserBoard;
import com.example.trelloeaglebrothers.entity.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {

    void deleteAllByCollaborator(User user);

    Optional<UserBoard> findById(Long id);

    Optional<UserBoard> findUserBoardByCollaborator_IdAndBoard(Long id, Board board);

    Optional<UserBoard> findUserBoardByCollaborator_Id (Long id);
}