package org.zerock.life_fit.board.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.life_fit.comment.domain.Comment;
import org.zerock.life_fit.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "FREEBOARD")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bno", updatable = false)
    private Long bno;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 작성자
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_id")
    private LocalCate localCate;

    @Column(name = "board_type")
    private String boardType;

    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regdate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime moddate;

    @Column(name = "visitcount")
    private int visitcount;

    @Column(name = "likes")
    private int likes;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    // Setter methods
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setVisitcount(int visitcount) {
        this.visitcount = visitcount;
    }

    public void setLocalCate(LocalCate localCate) {
        this.localCate = localCate;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setBoard(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setBoard(null);
    }
}
