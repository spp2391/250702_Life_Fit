package org.zerock.life_fit.mainscreen.cctv.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cctv_info")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CCTV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_name", length = 300)
    private String addressName;

    @Column(name = "location_info", length = 300)
    private String locationInfo;

    @Column(name = "cctv_group", length = 150)
    private String cctvGroup;
}

