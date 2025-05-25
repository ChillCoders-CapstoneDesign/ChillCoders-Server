package com.project.submate.subscribe.repository;

import com.project.submate.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
//    이름 기반으로 조회할 때 사용 가능
    Optional<Category> findByCategoryName(String categoryName);
}
