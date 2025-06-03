package com.project.submate.subscribe.service;

import com.project.submate.category.entity.Category;
import com.project.submate.subscribe.dto.SubscribeRequestDto;
import com.project.submate.subscribe.dto.SubscribeResponseDto;
import com.project.submate.subscribe.entity.ServiceInfo;
import com.project.submate.subscribe.entity.Subscribe;
import com.project.submate.subscribe.repository.CategoryRepository;
import com.project.submate.subscribe.repository.ServiceInfoRepository;
import com.project.submate.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final ServiceInfoRepository serviceInfoRepository;
    private final CategoryRepository categoryRepository;

    public List<SubscribeResponseDto> subscribeAllList() {
//        return subscribeRepository.findAllByUserId(1);
        List<Subscribe> subscribe = subscribeRepository.findAllByUserId(1);
        return subscribe.stream()
                .map(sub -> {
                    int dDay = calculateDday(sub.getStartDate());
                    return SubscribeResponseDto.from(sub, dDay);
                })
//                dDay 임박한 순으로 정렬한다.
                .sorted(Comparator.comparingInt(SubscribeResponseDto::getDDay))
                .toList();
    }

//    디데이 계산(******Asia/Seoul 시간으로 나오지 않아서 임의로 결과에 -1을 해주었다)
    private int calculateDday(LocalDate startDate) {
//        LocalDate.plusMonths: 자동으로 월의 마지막 날짜를 고려하여 계산한다.
        LocalDate baseDate = startDate.plusMonths(1); // 기준일: startDate + 1달
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        int dDay = (int) ChronoUnit.DAYS.between(now, baseDate);
        return Math.max(dDay - 1, 0);
    }

    public Optional<Subscribe> findBySubscribeNo(Integer subscribeNo) {
        return subscribeRepository.findBySubscribeNo(subscribeNo);
    }

    public Subscribe update(Integer subscribeNo, SubscribeRequestDto subscribeRequestDto) {
        Subscribe subscribe = subscribeRepository.findBySubscribeNo(subscribeNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 구독이 없습니다."));
        subscribeRequestDto.updateSubscribeInfo(subscribe); // 값 수정
        subscribe.setUserId(1);
        return subscribeRepository.save(subscribe);
    }

    public Subscribe save(SubscribeRequestDto subscribeRequestDto) {
        Category category;

        // 데이터베이스에 등록된 서비스명 확인
        Optional<ServiceInfo> serviceInfoOpt = serviceInfoRepository.findById(subscribeRequestDto.getName());

        if (serviceInfoOpt.isPresent()) {
            // 기존 서비스면 무조건 기존 매핑된 카테고리 사용
            category = serviceInfoOpt.get().getCategory();
        } else {
            // 새로운 서비스 등록이면 사용자가 선택한 categoryNo 기반으로 조회
            category = categoryRepository.findById(subscribeRequestDto.getCategoryNo())
                    .orElseThrow(() -> new IllegalArgumentException("해당 카테고리는 존재하지 않습니다."));

            // 새로운 서비스를 등록할 때 카테고리 매핑
            ServiceInfo serviceInfo = ServiceInfo.builder()
                    .serviceName(subscribeRequestDto.getName())
                    .category(category)
                    .build();
            serviceInfoRepository.save(serviceInfo);
        }

        // 구독 정보 저장
        Subscribe subscribe = Subscribe.builder()
                .subscribeName(subscribeRequestDto.getName())
                .image(subscribeRequestDto.getImage())
                .price(subscribeRequestDto.getPrice())
                .priceUnit(subscribeRequestDto.getPriceUnit())
                .period(subscribeRequestDto.getPeriod())
                .periodUnit(subscribeRequestDto.getPeriodUnit())
                .startDate(subscribeRequestDto.getStartDate())
                .isCollect("N")
                .category(category)
                .userId(1) //userId 1로 하드코딩
                .build();

        return subscribeRepository.save(subscribe);
    }
}
