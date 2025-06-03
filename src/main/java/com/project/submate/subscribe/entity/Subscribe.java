package com.project.submate.subscribe.entity;

import com.project.submate.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "SUBSCRIBE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBSCRIBE_NO")
    private Integer subscribeNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_NO", nullable = false)
    private Category category;

//    userId 하드코딩해서 적음(로그인 기능 아직 X)
    @Column(name = "USER_ID", nullable = false)
    private Integer userId;

    @Column(name = "SUBSCRIBE_NAME", length = 15, nullable = false)
    private String subscribeName;

    @Column(name = "IMAGE", length = 255)
    private String image;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "PRICE_UNIT", length = 1)
    private String priceUnit; // 예: ₩(원), $

    @Column(name = "PERIOD", length = 2)
    private Integer period; // 예: 1, 12

    @Column(name = "PERIOD_UNIT", length = 1)
    private String periodUnit; // 예: 월, 년

    @Column(name = "START_DATE")
    private LocalDate startDate;

//    @Column(name = "IS_DELETED")
//    private boolean isDeleted = false;

    @Column(name = "IS_COLLECT", length = 1)
    private String isCollect; // Y or N
}
