package com.example.trelloeaglebrothers.repository;

import com.example.trelloeaglebrothers.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
