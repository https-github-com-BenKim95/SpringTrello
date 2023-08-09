package com.example.trelloeaglebrothers.entity;

import com.example.trelloeaglebrothers.dto.CardRequestDto;
import com.example.trelloeaglebrothers.repository.UserCardRepository;
import com.example.trelloeaglebrothers.repository.UserRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//동규님
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "card")
public class Card extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String color;

    // 날짜 설정 필드 추가 -> JSON으로 {2023-09-12T15:30} 와 같이 입력 보내야함
    @Column
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private Long orderNum;

    // 다대다 관계를 중간 엔티티인 UserCard를 통해 설정하여 불러오기
    // 작업자 설정
    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<UserCard> userCardList = new ArrayList<>();

    // 카드 삭제시 카드 커맨트 모두 삭제하게끔 구현
    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<CardComment> cardComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_list_id")
    private ColumnList columnList;


    public Card (CardRequestDto cardRequestDto, Long orderNum, List<UserCard> userCardList, ColumnList columnList) {
        this.title = cardRequestDto.getTitle();
        this.description = cardRequestDto.getDescription();
        this.color = cardRequestDto.getColor();
        this.dueDate = cardRequestDto.getDueDate();
        this.orderNum = orderNum;
        this.userCardList = userCardList;
        this.columnList = columnList;
    }

    public void update(CardRequestDto cardRequestDto, UserCardRepository userCardRepository, UserRepository userRepository, Long cardId) {
        this.title = cardRequestDto.getTitle();
        this.description = cardRequestDto.getDescription();
        this.color = cardRequestDto.getColor();
        this.dueDate = cardRequestDto.getDueDate();

        List<UserCard> updatedUserCardList = new ArrayList<>();

        for (String username : cardRequestDto.getMembers()) {
            Optional<UserCard> userCardOptional = userCardRepository.findByUserUsernameAndCard_Id(username, cardId);

            if (userCardOptional.isEmpty()) {
                Optional<User> userOptional = userRepository.findByUsername(username);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    UserCard userCard = new UserCard(user, this);
                    userCardRepository.save(userCard);
                    updatedUserCardList.add(userCard);
                }
            } else {
                updatedUserCardList.add(userCardOptional.get());
            }
        }

        // cardRequestDto에 username이 들어오지 않으면 UserCard 레파지토리에서 삭제
        List<UserCard> userCardsToRemove = new ArrayList<>(userCardList);
        userCardsToRemove.removeAll(updatedUserCardList);
        userCardList.removeAll(userCardsToRemove);

        userCardRepository.deleteAll(userCardsToRemove);
    }
}