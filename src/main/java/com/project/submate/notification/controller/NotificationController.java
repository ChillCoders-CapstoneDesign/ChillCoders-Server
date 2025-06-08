package com.project.submate.notification.controller;

import com.project.submate.notification.entity.Notification;
import com.project.submate.notification.service.NotificationScheduler;
import com.project.submate.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

//    테스트용
    private final NotificationScheduler notificationScheduler;

    @GetMapping
    public List<Notification> getUnreadNotifications() {
        return notificationService.getUnreadNotification(1);
    }

    @PatchMapping("/{notificationNo}/read")
    public void markAsRead(@PathVariable Integer notificationNo) {
        notificationService.markAsRead(notificationNo);
    }

//    테스트용
    @GetMapping("/test-scheduler")
    public void runSchedulerManually() {
        notificationScheduler.checkNotifications();
    }


}
