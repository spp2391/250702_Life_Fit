package org.zerock.life_fit.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "LOCALCATE")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class LocalCate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "localnum", updatable = false)
    private int localnum;
    @Column(name = "localname", nullable = false)
    private String localname;
}
