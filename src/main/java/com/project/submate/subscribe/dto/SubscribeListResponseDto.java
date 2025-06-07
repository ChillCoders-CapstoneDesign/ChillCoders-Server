package com.project.submate.subscribe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SubscribeListResponseDto {
    private int totalCount;
    private int monthlyTotalPrice;
    private int yearlyTotalPrice;
    private List<SubscribeResponseDto> subscribeList;
}

