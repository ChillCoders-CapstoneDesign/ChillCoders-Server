package com.project.submate.subscribe.dto;

import com.project.submate.subscribe.entity.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SubscribeResponseDto {
    private Integer subscribeNo;
    private String subscribeName;
    private String image;
    private Integer price;
    private String priceUnit;
    private Integer period;
    private String periodUnit;
    private LocalDate startDate;
    private String isCollect;

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
                s.getIsCollect()
        );
    }
}
