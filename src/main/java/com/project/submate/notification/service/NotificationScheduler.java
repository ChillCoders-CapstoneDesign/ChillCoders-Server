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

    @Scheduled(cron = "0 0 9 * * *")  // 매일 오후 6시 (한국시간 기준)
    public void checkNotifications() {
        System.out.println("Scheduler 실행 시작");

        try {
            List<Subscribe> subscribeList = subscribeRepository.findAllByUserId(1);
//            System.out.println("구독 개수: " + subscribeList.size());

            LocalDate today = LocalDate.now();
            int totalSpending = 0;

            for (Subscribe s : subscribeList) {
                if (s == null) {
                    continue;
                }

                try {
//                    System.out.println("처리 중인 서비스: " + s.getSubscribeName());

                    if (s.getStartDate() == null || s.getSubscribeName() == null || s.getPrice() == null) {
                        continue;
                    }

                    long monthsPassed = ChronoUnit.MONTHS.between(s.getStartDate(), today);

                    LocalDate nextPayDate = s.getStartDate().plusMonths(monthsPassed);
                    if (!nextPayDate.isAfter(today)) {
                        nextPayDate = nextPayDate.plusMonths(1);
                    }

                    long dDay = ChronoUnit.DAYS.between(today, nextPayDate);

                    // 1. 3일 전 알림
                    if (dDay == 3) {
                        notificationService.saveNotification(1, s.getSubscribeName() + " 구독 결제가 3일 후 예정되어 있어요!");
                    }

                    // 2. 6개월 경과(사용하고 있음을) 알림
                    if (monthsPassed >= 6 && monthsPassed % 6 == 0) {
                        notificationService.saveNotification(1, s.getSubscribeName() + " 구독을 " + monthsPassed + "개월 동안 이어오셨어요. 잠깐 해지도 고려해보세요!");
                    }

                    // 3. 월 지출 누적합 계산
                    if (nextPayDate.getMonthValue() == today.getMonthValue() && nextPayDate.getYear() == today.getYear()) {
                        totalSpending += s.getPrice();
                    }

                } catch (Exception ex) {
//                    System.err.println("반복문 내부 처리 중 에러: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }

            // 3-2. 월 지출 3만원 초과
            if (totalSpending > 30000) {
                notificationService.saveNotification(1, "이번 달 구독 지출이 총 " + totalSpending + "원이에요. 구독하고 있는 서비스들을 점검해 보세요!");
//                System.out.println("지출 초과 알림: " + totalSpending + "원");
            }

        } catch (Exception e) {
            System.err.println("구독 목록 조회 중 에러: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Scheduler 실행 종료");
    }
}
