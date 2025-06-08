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

    @Scheduled(cron = "0 0 9 * * *") // 매일 오전 9시
    public void checkNotifications() {
        List<Subscribe> subs = subscribeRepository.findAllByUserId(1);
        LocalDate today = LocalDate.now();
        int totalSpending = 0;

        for (Subscribe s : subs) {
            long monthsPassed = ChronoUnit.MONTHS.between(s.getStartDate(), today);
            LocalDate nextPayDate = s.getStartDate().plusMonths(monthsPassed + 1);
            long dDay = ChronoUnit.DAYS.between(today, nextPayDate);

            // 디데이 3일 전
            if (dDay == 3) {
                notificationService.saveNotification(1, s.getSubscribeName() + " 구독 결제가 3일 후 예정되어 있어요!");
            }

            // 6개월 경과 알림 (매 6개월마다)
            if (monthsPassed >= 6 && monthsPassed % 6 == 0) {
                notificationService.saveNotification(1, s.getSubscribeName() + " 구독을 " + monthsPassed + "개월 동안 이어오셨어요. 잠깐 해지도 고려해보세요!");
            }

            // 이번 달 결제 발생 시 총합에 포함
            if (nextPayDate.getMonth() == today.getMonth() && nextPayDate.getYear() == today.getYear()) {
                totalSpending += s.getPrice();
            }
        }

        // 월 구독 지출이 3만원 초과
        if (totalSpending > 30000) {
            notificationService.saveNotification(1, "이번 달 구독 지출이 총 " + totalSpending + "원이에요. 다른 구독도 점검해보세요!");
        }
    }
}
