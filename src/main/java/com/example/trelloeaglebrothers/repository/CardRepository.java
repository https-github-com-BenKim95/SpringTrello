package com.example.trelloeaglebrothers.repository;

import com.example.trelloeaglebrothers.entity.Card;
import com.example.trelloeaglebrothers.entity.ColumnList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository <Card, Long> {
    List<Card> findAllByOrderByOrderNumAsc();
    List<Card> findAllByColumnListIdAndId(Long columList_id, Long cardId);
    Optional<Card> findByColumnListIdAndId(Long columList_id, Long cardId);
}
