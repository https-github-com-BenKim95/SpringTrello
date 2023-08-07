package com.example.trelloeaglebrothers;

import com.example.trelloeaglebrothers.entity.Board;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.entity.UserBoard;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    void saveTest() {
        User user1 = new User("Hyun","1234", "hjchoo95@naver.com");
        User user2 = new User("Joong","1234", "hjchoo95@naver.com");
        em.persist(user1);
        em.persist(user2);

        Board board1 = new Board("title");
        Board board2 = new Board("title2");
        em.persist(board1);
        em.persist(board2);

        UserBoard userBoard1 = new UserBoard();
        userBoard1.setUser(user1);
        userBoard1.setBoard(board1);
        em.persist(userBoard1);

        UserBoard userBoard2 = new UserBoard();
        userBoard2.setUser(user1);
        userBoard2.setBoard(board2);
        em.persist(userBoard2);


    }
}
