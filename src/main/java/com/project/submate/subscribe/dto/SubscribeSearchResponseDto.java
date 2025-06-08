package com.project.submate.subscribe.dto;

import com.project.submate.subscribe.entity.Subscribe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
@Schema(description = "입력한 이름으로 시작하는 구독 서비스 검색")
public class SubscribeSearchResponseDto {
    @Schema(description = "구독 번호", example = "1")
    private Integer subscribeNo;

    @Schema(description = "구독 이름", example = "넷플릭스")
    private String subscribeName;

    @Schema(description = "구독 로고", example = "S3에 배포한 netflix.png")
    private String image;

    public static SubscribeSearchResponseDto from(Subscribe subscribe) {
        return new SubscribeSearchResponseDto(
                subscribe.getSubscribeNo(),
                subscribe.getSubscribeName(),
                subscribe.getImage()
        );
    }
}
