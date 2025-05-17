package com.project.submate.subscribe.service;

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
}
