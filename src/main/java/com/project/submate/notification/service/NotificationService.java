package com.project.submate.notification.service;

import com.project.submate.notification.entity.Notification;
import com.project.submate.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void saveNotification(Integer userId, String message) {
        if (userId == null || message == null) return;
        notificationRepository.save(Notification.builder()
                .userId(userId)
                .message(message)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build());
    }

    public List<Notification> getUnreadNotification(Integer userId) {
        return notificationRepository.findAllByUserIdAndIsReadFalse(userId);
    }

    @Transactional
    public void markAsRead(Integer notificationNo) {
        notificationRepository.findById(notificationNo).ifPresent(n -> {
            n.setIsRead(true);
            notificationRepository.save(n);
        });
    }
    
}
