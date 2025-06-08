package com.project.submate.notification.controller;

import com.project.submate.notification.entity.Notification;
import com.project.submate.notification.service.NotificationScheduler;
import com.project.submate.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
@Tag(name = "Notification API", description = "알림 관리 API")
public class NotificationController {
    private final NotificationService notificationService;

//    테스트용
    private final NotificationScheduler notificationScheduler;

    @Operation(summary = "읽지 않은 알림 (목록) 조회", description = "userId=1 에 해당되는 알림이 조회된다." +
            "프론트에서는 toastify로 알림창이 띄워지도록 + 상단에 알림표시 로고를 눌렀을 때 알림 목록이 나오게 할 때 해당 API를 사용한다." +
            "알림은 총 3가지 유형이 있는데 예를 들어 다음과 같다." +
            "\"message\": \"벅스 구독 결제가 3일 후 예정되어 있어요!\"" +
            "\"message\": \"이번 달 구독 지출이 총 41900원이에요. 구독하고 있는 서비스들을 점검해 보세요!\"" +
            "\"message\": \"벅스 구독을 12개월 동안 이어오셨어요. 잠깐 해지도 고려해보세요!\"")
    @GetMapping
    public List<Notification> getUnreadNotifications() {
        return notificationService.getUnreadNotification(1);
    }

    @Operation(summary = "읽지 않은 알림 -> 읽음으로 표시", description = "is_read가 0인지 1인지 확인하여 읽지 않음과 읽음을 구분한다." +
            "사용자가 특정 알림을 클릭하였을 경우 읽음으로 표시하기 위한 API이다.")
    @PatchMapping("/{notificationNo}/read")
    public void markAsRead(@PathVariable Integer notificationNo) {
        notificationService.markAsRead(notificationNo);
    }

//    테스트용
    @Operation(summary = "(프론트에서 사용X)단순 백엔드 테스트용 API", description = "프론트에서 사용해야 하는 API 아님.")
    @GetMapping("/test-scheduler")
    public void runSchedulerManually() {
        notificationScheduler.checkNotifications();
    }
}
