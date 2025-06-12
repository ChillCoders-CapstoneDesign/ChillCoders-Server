package com.project.submate.subscribe.service;

import com.project.submate.category.entity.Category;
import com.project.submate.subscribe.dto.SubscribeCategoryListResponseDto;
import com.project.submate.subscribe.dto.SubscribeListResponseDto;
import com.project.submate.subscribe.dto.SubscribeRequestDto;
import com.project.submate.subscribe.dto.SubscribeResponseDto;
import com.project.submate.subscribe.entity.ServiceInfo;
import com.project.submate.subscribe.entity.Subscribe;
import com.project.submate.subscribe.repository.CategoryRepository;
import com.project.submate.subscribe.repository.ServiceInfoRepository;
import com.project.submate.subscribe.repository.SubscribeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final ServiceInfoRepository serviceInfoRepository;
    private final CategoryRepository categoryRepository;

    private static final int USD_TO_KRW = 1360; // 고정 환율

//    calculateMonthlyTotal, calculateYearlyTotal 메서드는 아래에 있다.
    public SubscribeListResponseDto subscribeAllList() {
        List<Subscribe> subscribeList = subscribeRepository.findAllByUserId(1);

        List<SubscribeResponseDto> result = subscribeList.stream()
                .map(sub -> SubscribeResponseDto.from(sub, calculateDday(sub.getStartDate(), sub.getPeriod(), sub.getPeriodUnit())))
                .sorted(Comparator.comparingInt(SubscribeResponseDto::getDDay))
                .toList();

        int totalCount = result.size();
        int monthlyTotal = calculateMonthlyTotal(subscribeList);
        int yearlyTotal = calculateYearlyTotal(subscribeList);

        return new SubscribeListResponseDto(
                totalCount,
                monthlyTotal,
                yearlyTotal,
                result
        );
    }

    //    디데이 계산
    private int calculateDday(LocalDate startDate, Integer period, String periodUnit) {
        if (startDate == null || period == null || period <= 0 || periodUnit == null) {
            return 0;
        }

        LocalDate now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDate();
        LocalDate nextBillingDate = startDate;

        while (!nextBillingDate.isAfter(now)) {
            switch (periodUnit) {
                case "달":
                    nextBillingDate = nextBillingDate.plusMonths(period);
                    break;
                case "년":
                    nextBillingDate = nextBillingDate.plusYears(period);
                    break;
                default:
                    return 0;
            }
        }

        return Math.max((int) ChronoUnit.DAYS.between(now, nextBillingDate), 0);
    }

    public Subscribe findBySubscribeNo(Integer subscribeNo) {
        return subscribeRepository.findBySubscribeNo(subscribeNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 구독 서비스가 없습니다."));
    }

    public SubscribeResponseDto update(Integer subscribeNo, SubscribeRequestDto subscribeRequestDto) {
        Subscribe subscribe = subscribeRepository.findBySubscribeNo(subscribeNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 구독이 없습니다."));
        subscribeRequestDto.updateSubscribeInfo(subscribe); // 값 수정
        subscribe.setUserId(1);
//        return subscribeRepository.save(subscribe);
        Subscribe updated = subscribeRepository.save(subscribe);
        return SubscribeResponseDto.from(updated, calculateDday(updated.getStartDate(), updated.getPeriod(), updated.getPeriodUnit()));
    }

    public SubscribeResponseDto save(SubscribeRequestDto subscribeRequestDto) {
        Category category;

        // 데이터베이스에 등록된 서비스명 확인
        Optional<ServiceInfo> serviceInfoOpt = serviceInfoRepository.findById(subscribeRequestDto.getName());

        if (serviceInfoOpt.isPresent()) {
            // 기존 서비스면 무조건 기존 매핑된 카테고리 사용
            category = serviceInfoOpt.get().getCategory();
            if (category == null) {
                throw new IllegalStateException("기존 서비스에 연결된 카테고리가 없습니다.");
            }
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

//        return subscribeRepository.save(subscribe);
        Subscribe saved = subscribeRepository.save(subscribe);
        int dDay = calculateDday(saved.getStartDate(), saved.getPeriod(), saved.getPeriodUnit());
        return SubscribeResponseDto.from(saved, dDay);
    }

//    카테고리별 목록조회
    public SubscribeCategoryListResponseDto getSubscribeByCategory(Integer categoryNo){
        Category category = categoryRepository.findById(categoryNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리는 존재하지 않습니다."));
        List<Subscribe> subscribeList = subscribeRepository.findAllByUserIdAndCategory(1, category);

        List<SubscribeResponseDto> result = subscribeList.stream()
                .map(sub -> SubscribeResponseDto.from(sub, calculateDday(sub.getStartDate(), sub.getPeriod(), sub.getPeriodUnit())))
                .toList();

        int totalCount = result.size();
        int monthlyTotal = calculateMonthlyTotal(subscribeList);
        int yearlyTotal = calculateYearlyTotal(subscribeList);

        return new SubscribeCategoryListResponseDto(
                categoryNo,
                totalCount,
                monthlyTotal,
                yearlyTotal,
                result
        );
    }

//    1달 금액 계산
    private int calculateMonthlyTotal(List<Subscribe> list) {
        int total = 0;
        for (Subscribe s : list) {
            if (!"달".equals(s.getPeriodUnit())) continue; // 달 단위만 계산한다.

            int price = s.getPrice();

            if ("$".equals(s.getPriceUnit()) || "달러".equals(s.getPriceUnit())) {
                price *= USD_TO_KRW;
            }

            price = price / s.getPeriod();

            total += price;
        }
        return total;
    }

//    1년 금액 계산
    private int calculateYearlyTotal(List<Subscribe> list) {
        int total = 0;
        for (Subscribe s : list) {
            if (!"년".equals(s.getPeriodUnit())) continue; // 년 단위만 계산한다.

            int price = s.getPrice();

            if ("$".equals(s.getPriceUnit()) || "달러".equals(s.getPriceUnit())) {
                price *= USD_TO_KRW;
            }

            price = price / s.getPeriod();

            total += price;
        }
        return total;
    }

    @Transactional
    public void delete(Integer subscribeNo) {
        int userId = 1;

        boolean exists = subscribeRepository.findBySubscribeNo(subscribeNo)
                .map(s -> s.getUserId().equals(userId))
                .orElse(false);

        if (!exists) {
            throw new IllegalArgumentException("해당 구독이 존재하지 않거나 사용자 권한이 없습니다.");
        }

        subscribeRepository.deleteBySubscribeNoAndUserId(subscribeNo, userId);
    }

    public List<Subscribe> searchByName(String keyword) {
        return subscribeRepository.findBySubscribeNameStartingWith(keyword);
    }
}
