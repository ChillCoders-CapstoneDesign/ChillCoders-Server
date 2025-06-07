package com.project.submate.subscribe.repository;

import com.project.submate.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
//    List<Subscribe> findAllByUserIdAndIsDeletedFalse(Integer userId);

    List<Subscribe> findAllByUserId(Integer userId);

    Optional<Subscribe> findBySubscribeNo(Integer subscribeNo);

    List<Subscribe> findAllByUserIdAndCategory(int i, Integer categoryNo);

//    현재는 userId를 1로 하드코딩했지만, 일반적으로 다른 userId를 삭제하면 안되기 때문에 이와 같이 적는다.
//    Optional<Subscribe> findBySubscribeNoAndUserId(Integer subscribeNo, Integer userId);
}
