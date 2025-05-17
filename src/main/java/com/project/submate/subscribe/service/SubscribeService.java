package com.project.submate.subscribe.service;

import com.project.submate.subscribe.dto.SubscribeRequestDto;
import com.project.submate.subscribe.dto.SubscribeResponseDto;
import com.project.submate.subscribe.entity.Subscribe;
import com.project.submate.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    public List<Subscribe> subscribeAllList() {
        return subscribeRepository.findAll();
    }

    public Optional<Subscribe> findBySubscribeNo(Integer subscribeNo) {
        return subscribeRepository.findBySubscribeNo(subscribeNo);
    }

    public Subscribe update(Integer subscribeNo, SubscribeRequestDto subscribeRequestDto) {
        Subscribe subscribe = subscribeRepository.findBySubscribeNo(subscribeNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 구독이 없습니다."));
        subscribeRequestDto.updateSubscribeInfo(subscribe); // 값 수정
        return subscribeRepository.save(subscribe);
    }
}
