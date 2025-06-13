package com.project.submate.notification.service;

import com.project.submate.subscribe.entity.Subscribe;
import com.project.submate.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationScheduler {
    private final SubscribeRepository subscribeRepository;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 9 * * *")  // 스케줄-매일 오전 9시(한국시간아님)
    public void checkNotifications() {
        System.out.println("Scheduler 실행 시작");

        try {
            List<Subscribe> subscribeList = subscribeRepository.findAllByUserId(1);
            LocalDate today = LocalDate.now();
            int totalSpending = 0;

            for (Subscribe s : subscribeList) {
                if (s == null || s.getStartDate() == null || s.getSubscribeName() == null || s.getPrice() == null) {
                    continue;
                }

                LocalDate nextPayDate;
                long dDay;

                // "달"인 경우 처리
                if ("달".equals(s.getPeriodUnit())) {
                    long monthsPassed = ChronoUnit.MONTHS.between(s.getStartDate(), today);
                    nextPayDate = s.getStartDate().plusMonths(monthsPassed * s.getPeriod());

                    if (!nextPayDate.isAfter(today)) {
                        nextPayDate = nextPayDate.plusMonths(s.getPeriod());
                    }

                    dDay = ChronoUnit.DAYS.between(today, nextPayDate);

                    // 6개월 이상 구독 유지 알림
                    long totalMonths = ChronoUnit.MONTHS.between(s.getStartDate(), today);
                    if (totalMonths >= 6 && totalMonths % 6 == 0) {
                        notificationService.saveNotification(1, s.getSubscribeName() + " 구독을 " + totalMonths + "개월 동안 이어오셨어요. 잠깐 해지도 고려해보세요!");
                    }

                }
                // "년"인 경우 처리
                else if ("년".equals(s.getPeriodUnit())) {
                    long yearsPassed = ChronoUnit.YEARS.between(s.getStartDate(), today);
                    nextPayDate = s.getStartDate().plusYears(yearsPassed * s.getPeriod());

                    if (!nextPayDate.isAfter(today)) {
                        nextPayDate = nextPayDate.plusYears(s.getPeriod());
                    }

                    dDay = ChronoUnit.DAYS.between(today, nextPayDate);

                    // 6개월 이상 구독 유지 알림
                    long totalMonths = ChronoUnit.MONTHS.between(s.getStartDate(), today);
                    if (totalMonths >= 6 && totalMonths % 6 == 0) {
                        notificationService.saveNotification(1, s.getSubscribeName() + " 구독을 " + totalMonths + "개월 동안 이어오셨어요. 잠깐 해지도 고려해보세요!");
                    }

                } else {
                    // 잘못된 periodUnit 값일 경우 skip
                    continue;
                }

                // 1. 디데이 3일 전 알림
                if (dDay == 4) {
                    notificationService.saveNotification(1, s.getSubscribeName() + " 구독 결제가 3일 후 예정되어 있어요!");
                }

                // 2. 이번 달에 결제 예정이면 지출 누적합에 추가
                if (nextPayDate.getMonthValue() == today.getMonthValue() && nextPayDate.getYear() == today.getYear()) {
                    int price = s.getPrice();
                    if ("$".equals(s.getPriceUnit())) {
                        price *= 1360; // 환산
                    }
                    totalSpending += price;
                }
            }

            // 3. 월간 지출 3만 원 초과 알림
            if (totalSpending > 30000) {
                notificationService.saveNotification(1, "이번 달 구독 지출이 총 " + totalSpending + "원이에요. 구독하고 있는 서비스들을 점검해 보세요!");
            }

        } catch (Exception e) {
            System.err.println("구독 목록 조회 중 에러: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Scheduler 실행 종료");
    }

}
