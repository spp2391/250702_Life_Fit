package org.zerock.life_fit.favorites.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Table(name="favorites")
@NoArgsConstructor
@Getter
@Entity
public class Favorites {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private int num;
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
}
