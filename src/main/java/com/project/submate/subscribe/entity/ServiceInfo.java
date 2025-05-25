package com.project.submate.subscribe.entity;

import com.project.submate.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SERVICE_INFO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceInfo {

    @Id
    @Column(name = "SERVICE_NAME", length = 20, nullable = false)
    private String serviceName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_NO", nullable = false)
    private Category category;
}
