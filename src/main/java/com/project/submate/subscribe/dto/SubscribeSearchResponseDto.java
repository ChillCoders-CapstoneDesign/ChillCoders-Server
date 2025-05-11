package com.project.submate.subscribe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Schema(description = "구독서비스 이름으로 검색")
public class SubscribeSearchResponseDto {
    @Schema(description = "구독서비스 번호", example = "1")
    private Integer submateNo;

    @Schema(description = "구독서비스 이름", example = "넷플릭스")
    private String name;

    @Schema(description = "구독서비스 이미지 URL", example = "https://netflix.com/logo.png")
    private String image;

    public Integer getSubmateNo() {
        return submateNo;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
