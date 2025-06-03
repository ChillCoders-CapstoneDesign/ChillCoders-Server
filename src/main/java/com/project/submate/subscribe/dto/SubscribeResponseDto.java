package com.project.submate.subscribe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.submate.category.entity.Category;
import com.project.submate.subscribe.entity.Subscribe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

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
    @Schema(description = "디데이", example = "7")
    private int dDay;
    @Schema(description = "경과한(지난) 개월 수", example = "9")
    private int passedMonth;


//    private String isCollect;

    public static SubscribeResponseDto from(Subscribe s, int dDay) {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        int passedMonth = calculateMonth(s.getStartDate(), now);

        return new SubscribeResponseDto(
                s.getSubscribeNo(),
                s.getSubscribeName(),
                s.getImage(),
                s.getPrice(),
                s.getPriceUnit(),
                s.getPeriod(),
                s.getPeriodUnit(),
                s.getStartDate(),
                s.getCategory().getCategoryNo(),
                dDay,
                passedMonth
//                s.getIsCollect()
        );
    }

    public static SubscribeResponseDto from(Subscribe s) {
//        int dDay = (int) ChronoUnit.DAYS.between(LocalDate.now(), s.getStartDate().plusMonths(1));
//        return from(s, dDay);
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate baseDate = s.getStartDate().plusMonths(1);
        int dDay = (int) ChronoUnit.DAYS.between(now, baseDate);

        return from(s, dDay);
    }

    private static int calculateMonth(LocalDate startDate, LocalDate now) {
        return (int) ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), now.withDayOfMonth(1));
    }

}
