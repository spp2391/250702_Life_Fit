package org.zerock.life_fit.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Table(name="favorite")
@NoArgsConstructor
@Getter
@ToString
@Entity
public class favorite {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private int num;
    /*@Column(name="num", updatable=false)
    private Long num;*/
    @Column(name="userId", nullable = false)
    private String userId;
    @Column(name="address", nullable = false)
    private String address;
    @Column(name="description")
    private String description;
    @CreatedDate
    @Column(name="regdate")
    private LocalDateTime regdate;
    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime moddate;
    @Column(name = "url")
    private String url;
    @Column(name = "title")
    private String title;

    @Builder
    public favorite(String userId, String address, String description,String url, String title) {
        this.userId = userId;
        this.address = address;
        if(!description.isEmpty()) {
            this.description = description;
        }
        this.regdate = LocalDateTime.now();
        this.url = url;
        this.title = title;
    }
    public void update(String address, String description) {
        this.address = address;
        this.description = description;
        this.moddate = LocalDateTime.now();
    }
}
