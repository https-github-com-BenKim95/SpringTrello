package com.example.trelloeaglebrothers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "user_board")
public class UserBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id", nullable = false)
//    private Board board;
//public UserBoard(User user, Board board) {
//    this.user = user;
//    this.board = board;
//}
    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collaborator_id", nullable = false)
    private User collaborator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public UserBoard(User collaborator, Board board,
                     UserRoleEnum role) {
        this.collaborator = collaborator;
        this.board = board;
        this.role = role;
    }



}