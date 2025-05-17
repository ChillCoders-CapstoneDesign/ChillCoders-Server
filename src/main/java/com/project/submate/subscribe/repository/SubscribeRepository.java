package com.project.submate.subscribe.repository;

import com.project.submate.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
    Optional<Subscribe> findBySubscribeNo(Integer subscribeNo);
}
