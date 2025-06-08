package com.project.submate.notification.repository;

import com.project.submate.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByUserIdAndIsReadFalse(Integer userId);

    @Query("SELECT COUNT(n) > 0 FROM Notification n WHERE n.userId = :userId AND n.message = :message AND FUNCTION('DATE', n.createdAt) = CURRENT_DATE")
    boolean existsTodayByUserIdAndMessage(@Param("userId") Integer userId, @Param("message") String message);
}
