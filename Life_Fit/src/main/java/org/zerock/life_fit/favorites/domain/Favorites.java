package org.zerock.life_fit.favorites.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Table(name="favorites")
@NoArgsConstructor
@Getter
@ToString
@Entity
public class Favorites {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="num", updatable=false)
    private Long num;
    @Column(name="userId", nullable = false)
    private String userId;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="address", nullable = false)
    private String address;
    @Column(name="description")
    private String description;
    @Column(name="url", nullable = false)
    private String url;
    @CreatedDate
    @Column(name="regdate")
    private LocalDateTime regdate;
    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime moddate;

    @Builder
    public Favorites(String userId, String name, String address, String description, String url) {
        this.userId = userId;
        this.address = address;
        if(name.isEmpty()) {
            this.name = address;
        } else {
            this.name = name;
        }
        if(!description.isEmpty()) {
            this.description = description;
        }
        this.url = url;
    }
    public void update(String description) {
        this.description = description;
    }
}
