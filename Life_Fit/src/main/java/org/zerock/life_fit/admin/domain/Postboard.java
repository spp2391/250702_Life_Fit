package org.zerock.life_fit.admin.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import org.zerock.life_fit.user.domain.User;

@Entity
@Table(name = "freeboard")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Postboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "board_type")
    private String boardType;

    @Column(name = "user_id")
    private String userId;

    private int visitcount;

    @Column(name = "local_id")
    private Long localId;

    private LocalDateTime regdate;
    private LocalDateTime moddate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
