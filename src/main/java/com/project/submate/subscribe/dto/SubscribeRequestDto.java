package com.project.submate.subscribe.dto;

import com.project.submate.subscribe.entity.Subscribe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SubscribeRequestDto {
    @Schema(description = "구독 이름", example = "넷플릭스")
    private String name;
    @Schema(description = "구독 로고", example = "S3에 배포한 netflix.png")
    private String image;
    @Schema(description = "구독 요금", example = "12000")
    private Integer price;
    @Schema(description = "구독 가격단위", example = "원")
    private String priceUnit;
    @Schema(description = "구독 주기", example = "1")
    private Integer period;
    @Schema(description = "구독 주기 단위", example = "달")
    private String periodUnit;
    @Schema(description = "구독 시작 날짜", example = "2025-05-17")
    private LocalDate startDate;
    @Schema(description = "카테고리 번호", example = "1.OTT 2.음악 3.배달/배송 4.클라우드 5.AI 6.툴 7.기타서비스")
    private Integer categoryNo;

    public void updateSubscribeInfo(Subscribe subscribe){
        subscribe.setSubscribeName(this.name);
        subscribe.setImage(this.image);
        subscribe.setPrice(this.price);
        subscribe.setPriceUnit(this.priceUnit);
        subscribe.setPeriod(this.period);
        subscribe.setPeriodUnit(this.periodUnit);
        subscribe.setStartDate(this.startDate);
    }
}
