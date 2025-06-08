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
        } catch (Exception e) {
            System.err.println("구독 목록 조회 중 에러: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Scheduler 실행 종료");
    }
}
