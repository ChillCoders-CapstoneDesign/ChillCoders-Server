package com.project.submate.notification.repository;

import com.project.submate.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByUserIdAndIsReadFalse(Integer userId);
}
