package com.project.submate.subscribe.repository;

import com.project.submate.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
    List<Subscribe> findAllByUserId(Integer userId);

    Optional<Subscribe> findBySubscribeNo(Integer subscribeNo);

//    Optional<Subscribe> findBySubscribeNoAndUserId(Integer subscribeNo, Integer userId);
}
