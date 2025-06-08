package com.project.submate.subscribe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "등록한 구독 서비스 카테고리별 조회")
public class SubscribeCategoryListResponseDto {
    @Schema(description = "카테고리 번호", example = "1.OTT 2.음악 3.배달/배송 4.클라우드 5.AI 6.툴 7.기타서비스")
    private int categoryNo;
    @Schema(description = "카테고리별 구독 개수", example = "7")
    private int totalCount;
    @Schema(description = "카테고리별 월간 지출 금액", example = "35000")
    private int monthlyTotalPrice;
    @Schema(description = "카테고리별 연간 지출 금액", example = "87000")
    private int yearlyTotalPrice;
    private List<SubscribeResponseDto> subscribeList;
}
