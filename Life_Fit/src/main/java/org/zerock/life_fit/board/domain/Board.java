package org.zerock.life_fit.board.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "BOARD")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bno", updatable = false)
    private int bno;
    @Column(name = "cateno", nullable = false)
    private int cateno;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name="content", nullable = false)
    private String content;
    @Column(name="writer", nullable = false)
    private String writer;
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
    @Column(name = "url")
    private String url;

    @Builder
    public Board(String title, String content, String writer,  LocalDateTime regdate, LocalDateTime moddate, int visitcount, int likes, String url) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.regdate = regdate;
        this.moddate = moddate;
        this.visitcount = visitcount;
        this.likes = likes;
        this.url = url;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
