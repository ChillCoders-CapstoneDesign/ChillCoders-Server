//package com.project.submate.notification.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Notification {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer notificationNo;
//
//    private Integer userId;
//
//    private String message;
//
//    private Boolean isRead;
//
//    private LocalDateTime createdAt;
//
//    @PrePersist
//    public void prePersist() {
//        this.createdAt = LocalDateTime.now();
//        if (this.isRead == null) this.isRead = false;
//    }
//}
