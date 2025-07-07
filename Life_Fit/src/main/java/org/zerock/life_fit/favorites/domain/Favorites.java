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

    @Builder
    public Favorites(String userId, String address, String description) {
        this.userId = userId;
        this.address = address;
        if(!description.isEmpty()) {
            this.description = description;
        }
    }
    public void update(String address, String description) {
        this.address = address;
        this.description = description;
    }
}
