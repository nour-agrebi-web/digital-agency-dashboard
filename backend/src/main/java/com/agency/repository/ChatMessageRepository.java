package com.agency.repository;

import com.agency.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByProjectId(Long projectId);
    List<ChatMessage> findByUserId(Long userId);
}
