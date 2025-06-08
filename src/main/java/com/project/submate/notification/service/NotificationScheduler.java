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

    @Scheduled(cron = "0 0 9 * * *")
    public void checkNotifications() {
        try {
            List<Subscribe> subs = subscribeRepository.findAllByUserId(1);
            LocalDate today = LocalDate.now();
            int totalSpending = 0;

            for (Subscribe s : subs) {
                if (s.getStartDate() == null || s.getSubscribeName() == null || s.getPrice() == null) continue; // null 방어

                long monthsPassed = ChronoUnit.MONTHS.between(s.getStartDate(), today);
                LocalDate nextPayDate = s.getStartDate().plusMonths(monthsPassed + 1);
                long dDay = ChronoUnit.DAYS.between(today, nextPayDate);

                if (dDay == 3) {
                    notificationService.saveNotification(1, s.getSubscribeName() + " 구독 결제가 3일 후 예정되어 있어요!");
                }

                if (monthsPassed >= 6 && monthsPassed % 6 == 0) {
                    notificationService.saveNotification(1, s.getSubscribeName() + " 구독을 " + monthsPassed + "개월 동안 이어오셨어요. 잠깐 해지도 고려해보세요!");
                }

                if (nextPayDate.getMonth() == today.getMonth() && nextPayDate.getYear() == today.getYear()) {
                    totalSpending += s.getPrice();
                }
            }

            if (totalSpending > 30000) {
                notificationService.saveNotification(1, "이번 달 구독 지출이 총 " + totalSpending + "원이에요. 다른 구독도 점검해보세요!");
            }
        } catch (Exception e) {
            System.err.println("NotificationScheduler 예외 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
