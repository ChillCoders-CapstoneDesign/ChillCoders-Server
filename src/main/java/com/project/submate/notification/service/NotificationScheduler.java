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

    @Scheduled(cron = "0 0 9 * * *")  // 매일 오전 9시
    public void checkNotifications() {
        System.out.println("Scheduler 실행 시작");

        try {
            List<Subscribe> subs = subscribeRepository.findAllByUserId(1);
            System.out.println("구독 개수: " + subs.size());

            for (Subscribe s : subs) {
                if (s == null) {
                    System.out.println("null Subscribe 객체 발견 - continue");
                    continue;
                }

                try {
                    System.out.println("처리 중인 서비스: " + s.getSubscribeName());

                    if (s.getStartDate() == null || s.getSubscribeName() == null || s.getPrice() == null) {
                        System.out.println("필드 누락된 구독 건너뜀: " + s);
                        continue;
                    }

                    long monthsPassed = ChronoUnit.MONTHS.between(s.getStartDate(), LocalDate.now());
                    System.out.println("시작일: " + s.getStartDate() + ", 경과 개월 수: " + monthsPassed);
                    LocalDate nextPayDate = s.getStartDate().plusMonths(monthsPassed + 1);
                    long dDay = ChronoUnit.DAYS.between(LocalDate.now(), nextPayDate);

                    System.out.println("시작일: " + s.getStartDate() + ", 경과 개월 수: " + monthsPassed + ", dDay: " + dDay);

                    // 3일 전 알림
                    if (dDay == 3) {
                        notificationService.saveNotification(1, s.getSubscribeName() + " 구독 결제가 3일 후 예정되어 있어요!");
                        System.out.println("📨 디데이 알림 저장됨: " + s.getSubscribeName());
                    }

                } catch (Exception ex) {
                    System.err.println("반복문 내부 처리 중 에러: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.err.println("구독 목록 조회 중 에러: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Scheduler 실행 종료");
    }
}
