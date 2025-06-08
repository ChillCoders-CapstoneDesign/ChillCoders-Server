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

    @Operation(summary = "읽지 않은 알림 (목록) 조회", description = "userId=1 에 해당되는 알림이 조회된다.")
    @GetMapping
    public List<Notification> getUnreadNotifications() {
        return notificationService.getUnreadNotification(1);
    }

    @Operation(summary = "읽지 않은 알림 -> 읽음으로 표시", description = "is_read가 0인지 1인지 구분한다.")
    @PatchMapping("/{notificationNo}/read")
    public void markAsRead(@PathVariable Integer notificationNo) {
        notificationService.markAsRead(notificationNo);
    }

//    테스트용
    @Operation(summary = "단순 백엔드 테스트용 API", description = "프론트에서 사용해야 하는 API 아님.")
    @GetMapping("/test-scheduler")
    public void runSchedulerManually() {
        notificationScheduler.checkNotifications();
    }
}
