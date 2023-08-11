package com.example.trelloeaglebrothers.repository;

import com.example.trelloeaglebrothers.entity.CardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpAnd;

import java.util.Optional;

public interface CardCommentRepository extends JpaRepository<CardComment, Long> {
    Optional<CardComment> findByIdAndUserId (Long cardCommentId, Long userId);
}