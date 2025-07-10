//package org.zerock.life_fit.admin.domain;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Table(name = "admincomment")
//public class PostComment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long cno;
//
//    private Long bno;
//
//    @Column(name = "user_id")
//    private String writer;
//
//    @Column(length = 1000)
//    private String comment;
//
//    private LocalDateTime regdate;
//
//    private LocalDateTime moddate;
//}