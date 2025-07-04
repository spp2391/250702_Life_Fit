package org.zerock.life_fit.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.life_fit.user.domain.User;

import java.time.LocalDateTime;

@Table(name = "FREEBOARD")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class FreeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bno", updatable = false)
    private int bno;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name="content", nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @CreatedDate
    @Column(name="regdate")
    private LocalDateTime regdate;
    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime moddate;
    @Column(name = "visitcount")
    private int visitcount;
    @Column(name = "likes")
    private int likes;
}
