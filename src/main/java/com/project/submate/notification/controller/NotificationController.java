package com.project.submate.notification.controller;

import com.project.submate.notification.entity.Notification;
import com.project.submate.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public List<Notification> getUnreadNotifications() {
        return notificationService.getUnreadNotification(1);
    }

    @PatchMapping("/{notificationNo}/read")
    public void markAsRead(@PathVariable Integer notificationNo) {
        notificationService.markAsRead(notificationNo);
    }

}
