package org.zerock.life_fit.comment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name ="comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cno", nullable = false)
    private Long cno;

    @Column(name = "comment", nullable = false, length = 255)
    private String comment;

    // 일단 주석
   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;*/

    // 일단주석
 /*   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno", nullable = false)
    private Board board;*/

    @Column(name = "regdate")
    private LocalDateTime regdate;

    @Column(name = "moddate")
    private LocalDateTime moddate;
}

