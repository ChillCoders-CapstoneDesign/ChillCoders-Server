package com.project.submate.category.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CATEGORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_NO")
    private Integer categoryNo;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;
}
