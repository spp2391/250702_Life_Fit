package org.zerock.life_fit.mainscreen.station.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * 지하철 역 기본 정보 엔터티
 */
@Entity
@Table(name = "busan_station")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusanStation {

    /** 역 고유 코드(Primary Key) */
    @Id
    @Column(name = "station_code", nullable = false, length = 10)
    private Long stationCode;

    /** 역 한글명 */
    @Column(name = "station_name", nullable = false, length = 100)
    private String stationName;

    /** 역 영문명 */
    @Column(name = "station_name_eng", length = 100)
    private String stationNameEng;

    /** 역 대표 전화번호 */
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    /** 역 주소 */
    @Column(name = "station_address", length = 255)
    private String stationAddress;

    /** 환승 주차장 유무 */
    @Column(name = "transfer_parking")
    private String transferParking;

    /** 자전거 보관소 유무 */
    @Column(name = "bicycle_parking")
    private String bicycleParking;

    /** 물품 보관함 유무 */
    @Column(name = "locker")
    private String locker;

    /** 자동 사진기(증명사진기) 유무 */
    @Column(name = "photo_booth")
    private String photoBooth;

    /** 도시철도 경찰대 유무 */
    @Column(name = "metro_police_office")
    private String metroPoliceOffice;

    /** 섬식형(아일랜드형) 승강장 여부 */
    @Column(name = "island_platform")
    private String islandPlatform;

    /** 엘리베이터 유무 */
    @Column(name = "elevator")
    private String elevator;

    /** 역명 및 지명 유래(설명) */
    @Lob
    @Column(name = "station_origin_meaning")
    private String stationOriginMeaning;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
