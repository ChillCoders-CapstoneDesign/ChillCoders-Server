//package com.project.submate.notification.service;
//
//import com.project.submate.notification.entity.Notification;
//import com.project.submate.notification.repository.NotificationRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class NotificationService {
//
//    private final NotificationRepository notificationRepository;
//
//    public void saveNotification(Integer userId, String message) {
//        notificationRepository.save(Notification.builder()
//                .userId(userId)
//                .message(message)
//                .isRead(false)
//                .createdAt(LocalDateTime.now())
//                .build());
//    }
//
//    public List<Notification> getUnreadNotification(Integer userId) {
//        return notificationRepository.findAllByUserIdAndIsReadFalse(userId);
//    }
//
//    public void markAsRead(Integer notificationNo) {
//        notificationRepository.findById(notificationNo).ifPresent(n -> {
//            n.setIsRead(true);
//            notificationRepository.save(n);
//        });
//    }
//
//    public void markAllAsRead(Integer userId) {
//        List<Notification> list = notificationRepository.findAllByUserIdAndIsReadFalse(userId);
//        list.forEach(n -> n.setIsRead(true));
//        notificationRepository.saveAll(list);
//    }
//}
