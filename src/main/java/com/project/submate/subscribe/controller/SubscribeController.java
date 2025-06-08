package com.project.submate.subscribe.controller;

import com.project.submate.subscribe.dto.*;
import com.project.submate.subscribe.entity.Subscribe;
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
    @Operation(summary = "구독 서비스 목록 조회", description = "본인(userId=1)이 등록한 구독 서비스를 조회한다.")
    @GetMapping("/list")
    public ResponseEntity<SubscribeListResponseDto> getAllSubscribe(){
        return ResponseEntity.ok(subscribeService.subscribeAllList());
//                .stream()
//                .map(SubscribeResponseDto::from)
//                .toList();
    }

//    구독서비스 기존 정보 바탕 불러오기: 이 부분은 userId에 관계없이 db에 있는 것을 불러와야 하기 때문에 'findBySubscribeNo'를 사용한다.
    @Operation(summary = "기존 등록: 특정 구독 서비스 조회(구독 번호로 조회)", description = "기존(DB에 저장되어 있는)에 있는 구독 서비스 정보를 불러온다")
    @GetMapping("/{subscribeNo}")
    public ResponseEntity<SubscribeResponseDto> getBySubscribeNo(@PathVariable Integer subscribeNo){
        Subscribe subscribe = subscribeService.findBySubscribeNo(subscribeNo);
        return ResponseEntity.ok(SubscribeResponseDto.from(subscribe));
    }

//    구독서비스 기존 정보 바탕으로 저장/등록(즉, 수정)
    @Operation(summary = "구독 서비스 기존 등록", description = "기존 데이터를 바탕으로 사용자(userId=1)가 구독 서비스를 등록/수정한다.")
    @PutMapping("/{subscribeNo}")
    public SubscribeResponseDto updateSubscribe(
            @PathVariable Integer subscribeNo,
            @RequestBody SubscribeRequestDto subscribeRequestDto){
        Subscribe updateInfo = subscribeService.update(subscribeNo, subscribeRequestDto);
        return SubscribeResponseDto.from(updateInfo);
    }

//    구독서비스 새로 등록
    @Operation(summary = "구독 서비스를 새로 등록", description = "기존 서비스에 없던 구독 서비스를 사용자(userId=1)가 새로 등록한다.")
    @PostMapping("/create")
    public ResponseEntity<SubscribeResponseDto> create(@RequestBody SubscribeRequestDto subscribeRequestDto){
        Subscribe saved = subscribeService.save(subscribeRequestDto);
        return ResponseEntity.ok(SubscribeResponseDto.from(saved));
    }

//    카테고리별 구독서비스 목록조회
    @Operation(summary = "카테고리별 구독 목록 조회", description = "특정 카테고리(categoryNo)에 해당하는 구독 목록을 조회한다.")
    @GetMapping("/list/category/{categoryNo}")
    public ResponseEntity<SubscribeCategoryListResponseDto> getByCategory(@PathVariable Integer categoryNo) {
        return ResponseEntity.ok(subscribeService.getSubscribeByCategory(categoryNo));
    }

//    구독서비스 삭제
    @Operation(summary = "구독 서비스 삭제", description = "사용자(userId=1)의 특정 구독 서비스(구독 번호)를 삭제한다.")
    @DeleteMapping("/{subscribeNo}")
    public ResponseEntity<Void> deleteSubscribe(@PathVariable Integer subscribeNo) {
        subscribeService.delete(subscribeNo);
        return ResponseEntity.noContent().build();
    }

//    구독서비스 검색
    @Operation(summary = "구독 서비스 이름 검색", description = "입력한 이름으로 시작하는 구독 서비스를 검색한다.")
    @GetMapping("/search")
    public List<SubscribeSearchResponseDto> searchByName(@RequestParam String keyword) {
        List<Subscribe> result = subscribeService.searchByName(keyword);
        return result.stream()
                .map(SubscribeSearchResponseDto::from)
                .toList();
    }
}
