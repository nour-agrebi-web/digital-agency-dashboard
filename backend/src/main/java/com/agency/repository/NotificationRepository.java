package com.agency.repository;

import com.agency.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByUserIdAndReadStatusFalse(Long userId);
    long countByUserIdAndReadStatusFalse(Long userId);
}
