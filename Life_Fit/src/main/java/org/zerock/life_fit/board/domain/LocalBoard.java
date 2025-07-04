package org.zerock.life_fit.board.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.life_fit.user.domain.User;

import java.time.LocalDateTime;

@Table(name = "LOCALBOARD")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class LocalBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bno", updatable = false)
    private int bno;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localnum", nullable = false)
    private LocalCate localnum;
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

   /* @Builder
    public Board(String title, String content, String writer,  LocalDateTime regdate, LocalDateTime moddate, int visitcount, int likes, String url) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.regdate = regdate;
        this.moddate = moddate;
        this.visitcount = visitcount;
        this.likes = likes;
        this.url = url;
    }*/

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
