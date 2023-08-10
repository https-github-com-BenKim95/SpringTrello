package com.example.trelloeaglebrothers;
import com.example.trelloeaglebrothers.entity.Board;
import com.example.trelloeaglebrothers.entity.ColumnList;
import com.example.trelloeaglebrothers.entity.User;
import com.example.trelloeaglebrothers.entity.UserBoard;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    EntityManager em;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Rollback(value = false)
    void saveTest() {
        User user1 = new User("spring1", passwordEncoder.encode("1234"), "spring1@naver.com", "spring");
        User user2 = new User("spring2", passwordEncoder.encode("1234"), "spring1@naver.com", "spring");
        User user3 = new User("spring3", passwordEncoder.encode("1234"), "spring1@naver.com", "spring");
        User user4 = new User("spring4", passwordEncoder.encode("1234"), "spring1@naver.com", "spring");
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);



        Board board1 = new Board("title","");

        ColumnList columnList = new ColumnList(new Board());
        columnList.setOrderNum(1L);
        em.persist(columnList);



//
//        Board board2 = new Board("title2", "");
//        em.persist(board1);
//        em.persist(board2);
//
//        UserBoard userBoard1 = new UserBoard();
//        userBoard1.setUser(user1);
//        userBoard1.setBoard(board1);
//        em.persist(userBoard1);
//
//        UserBoard userBoard2 = new UserBoard();
//        userBoard2.setUser(user1);
//        userBoard2.setBoard(board2);
//        em.persist(userBoard2);

    }
}

