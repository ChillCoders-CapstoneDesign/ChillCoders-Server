package com.project.submate.subscribe.dto;

import com.project.submate.category.entity.Category;
import com.project.submate.subscribe.entity.Subscribe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Schema(description = "등록한 구독 서비스 조회")
public class SubscribeResponseDto {
    @Schema(description = "구독 번호", example = "1")
    private Integer subscribeNo;
    @Schema(description = "구독 이름", example = "넷플릭스")
    private String subscribeName;
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
    private Integer categoryNo;
//    private String isCollect;

    public static SubscribeResponseDto from(Subscribe s) {
        return new SubscribeResponseDto(
                s.getSubscribeNo(),
                s.getSubscribeName(),
                s.getImage(),
                s.getPrice(),
                s.getPriceUnit(),
                s.getPeriod(),
                s.getPeriodUnit(),
                s.getStartDate(),
                s.getCategory().getCategoryNo()
//                s.getIsCollect()
        );
    }
}
