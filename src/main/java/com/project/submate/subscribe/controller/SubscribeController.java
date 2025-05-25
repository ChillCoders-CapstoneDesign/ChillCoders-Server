package com.project.submate.subscribe.controller;

import com.project.submate.subscribe.dto.SubscribeRequestDto;
import com.project.submate.subscribe.dto.SubscribeResponseDto;
import com.project.submate.subscribe.dto.SubscribeSearchResponseDto;
import com.project.submate.subscribe.entity.Subscribe;
import com.project.submate.subscribe.repository.SubscribeRepository;
import com.project.submate.subscribe.service.SubscribeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscribe")
@RequiredArgsConstructor
@Tag(name = "Subscribe API", description = "구독 관리 API")
public class SubscribeController {

    private final SubscribeService subscribeService;

//    구독서비스 목록조회(홈에서 사용할 부분들)
    @Operation(summary = "구독 서비스 목록 조회", description = "본인이 등록한 구독 서비스를 조회한다.")
    @GetMapping("/list")
    public List<SubscribeResponseDto> getAllSubscribe(){
        return subscribeService.subscribeAllList()
                .stream()
                .map(SubscribeResponseDto::from)
                .toList();
    }

//    구독서비스 기존 정보 바탕 불러오기
    @Operation(summary = "기존 등록: 특정 구독 서비스 조회", description = "기존에 있는 구독 서비스를 등록/저장할 때 사용한다.")
    @GetMapping("/{subscribeNo}")
    public SubscribeResponseDto getBySubscribeNo(@PathVariable Integer subscribeNo){
        Subscribe subscribe = subscribeService.findBySubscribeNo(subscribeNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 구독 서비스가 없습니다."));
//        해야할 것: 해당 구독서비스 없을 때 에러처리 하기
        return SubscribeResponseDto.from(subscribe);
    }


//    구독서비스 기존 정보 바탕으로 저장/등록(즉, 수정)
    @Operation(summary = "구독 서비스 기존 등록", description = "기존 데이터를 바탕으로 구독 서비스를 등록/수정한다.")
    @PutMapping("/{subscribeNo}")
    public SubscribeResponseDto updateSubscribe(
            @PathVariable Integer subscribeNo,
            @RequestBody SubscribeRequestDto subscribeRequestDto){
        Subscribe updateInfo = subscribeService.update(subscribeNo, subscribeRequestDto);
        return SubscribeResponseDto.from(updateInfo);
    }

//    구독서비스 새로 등록
    @PostMapping("/create")
    public ResponseEntity<SubscribeResponseDto> create(@RequestBody SubscribeRequestDto subscribeRequestDto){
        Subscribe saved = subscribeService.save(subscribeRequestDto);
        return ResponseEntity.ok(SubscribeResponseDto.from(saved));
    }

//    구독서비스 검색
//    @Operation(summary = "구독 서비스 이름 검색", description = "입력한 이름으로 구독 서비스를 검색한다.")
//    @GetMapping("/search")
//    public List<SubscribeSearchResponseDto> searchByName(@RequestParam String name) {
//        return null;
//    }
}
