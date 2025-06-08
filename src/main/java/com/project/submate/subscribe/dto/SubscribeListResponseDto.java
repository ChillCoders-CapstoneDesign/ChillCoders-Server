package com.project.submate.subscribe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "등록한 구독 서비스 (전체)조회")
public class SubscribeListResponseDto {
    @Schema(description = "등록한 전체 구독 개수", example = "7")
    private int totalCount;
    @Schema(description = "등록한 전체 구독의 월간 총 지출 금액", example = "35000")
    private int monthlyTotalPrice;
    @Schema(description = "등록한 전체 구독의 연간 총 지출 금액", example = "87000")
    private int yearlyTotalPrice;
    private List<SubscribeResponseDto> subscribeList;
}

