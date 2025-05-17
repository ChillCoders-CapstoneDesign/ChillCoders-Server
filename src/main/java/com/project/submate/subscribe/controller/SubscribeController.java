package com.project.submate.subscribe.controller;

import com.project.submate.subscribe.dto.SubscribeResponseDto;
import com.project.submate.subscribe.dto.SubscribeSearchResponseDto;
import com.project.submate.subscribe.entity.Subscribe;
import com.project.submate.subscribe.repository.SubscribeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscribe")
@RequiredArgsConstructor
@Tag(name = "Subscribe API", description = "구독 관리 API")
public class SubscribeController {

    private final SubscribeRepository subscribeRepository;

    @GetMapping("/list")
    public List<SubscribeResponseDto> getAllSubscribe(){
        return subscribeRepository.findAll()
                .stream()
                .map(SubscribeResponseDto::from)
                .toList();
    }

    @Operation(summary = "구독 이름 검색", description = "입력한 이름으로 구독 서비스를 검색한다.")
    @GetMapping("/search")
    public List<SubscribeSearchResponseDto> searchByName(@RequestParam String name) {
        return null;
    }
}
