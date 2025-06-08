//package com.project.submate.notification.service;
//
//import com.project.submate.subscribe.entity.Subscribe;
//import com.project.submate.subscribe.repository.SubscribeRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class NotificationScheduler {
//    private final SubscribeRepository subscribeRepository;
//    private final NotificationService notificationService;
//
//    @Scheduled(cron = "0 0 9 * * *")  // 매일 오전 9시
//    public void checkNotifications() {
//        try {
//            System.out.println("NotificationScheduler 실행 시작");
//
//            List<Subscribe> subs = subscribeRepository.findAllByUserId(1);
//            LocalDate today = LocalDate.now();
//            int totalSpending = 0;
//
//            for (Subscribe s : subs) {
//                if (s == null) continue;
//
//                try {
//                    if (s.getStartDate() == null || s.getSubscribeName() == null || s.getPrice() == null) {
//                        System.out.println("누락된 데이터로 인해 건너뜀: " + s);
//                        continue;
//                    }
//
//                    long monthsPassed = ChronoUnit.MONTHS.between(s.getStartDate(), today);
//                    LocalDate nextPayDate = s.getStartDate().plusMonths(monthsPassed + 1);
//                    long dDay = ChronoUnit.DAYS.between(today, nextPayDate);
//
//                    if (dDay == 3) {
//                        notificationService.saveNotification(1, s.getSubscribeName() + " 구독 결제가 3일 후 예정되어 있어요!");
//                    }
//
//                    if (monthsPassed >= 6 && monthsPassed % 6 == 0) {
//                        notificationService.saveNotification(1, s.getSubscribeName() + " 구독을 " + monthsPassed + "개월 동안 이어오셨어요. 잠깐 해지도 고려해보세요!");
//                    }
//
//                    if (nextPayDate.getMonth() == today.getMonth() && nextPayDate.getYear() == today.getYear()) {
//                        totalSpending += s.getPrice();
//                    }
//                } catch (Exception inner) {
//                    System.err.println("개별 구독 처리 중 오류 발생: " + inner.getMessage());
//                    inner.printStackTrace();
//                }
//            }
//
//            if (totalSpending > 30000) {
//                notificationService.saveNotification(1, "이번 달 구독 지출이 총 " + totalSpending + "원이에요. 다른 구독도 점검해보세요!");
//            }
//
//            System.out.println("NotificationScheduler 정상 종료");
//
//        } catch (Exception e) {
//            System.err.println("전체 NotificationScheduler 예외 발생: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
