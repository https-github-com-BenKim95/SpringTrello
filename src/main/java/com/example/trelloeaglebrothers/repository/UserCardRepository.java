package com.example.trelloeaglebrothers.repository;

import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCardRepository extends JpaRepository<UserCard,Long> {
    List<UserCard> findAllByCardId (Long id);
    Optional<UserCard> findByUserUsernameAndCard_Id(String username, Long id);
    Optional<UserCard> findByUserIdAndCardId(Long userID, Long cardID);
}
