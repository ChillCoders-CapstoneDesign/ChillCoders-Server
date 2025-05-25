package com.project.submate.subscribe.repository;

import com.project.submate.subscribe.entity.ServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, String> {
}
