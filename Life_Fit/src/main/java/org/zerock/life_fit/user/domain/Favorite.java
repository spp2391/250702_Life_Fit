package org.zerock.life_fit.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Table(name = "favorite")
@NoArgsConstructor
@Getter
@ToString
@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @CreatedDate
    @Column(name = "regdate")
    private LocalDateTime regdate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime moddate;

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Builder
    public Favorite(User user, String address, String description, String url, String title, Double lat, Double lng) {
        this.user = user;
        this.address = address;
        if (description != null && !description.isEmpty()) {
            this.description = description;
        }
        this.regdate = LocalDateTime.now();
        this.url = url;
        this.title = title;
        this.lat = lat;
        this.lng = lng;
    }

    public void update(String address, String description) {
        this.address = address;
        this.description = description;
        this.moddate = LocalDateTime.now();
    }
}
