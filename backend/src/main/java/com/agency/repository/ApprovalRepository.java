package com.agency.repository;

import com.agency.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByProjectId(Long projectId);
    List<Approval> findByStatus(Approval.ApprovalStatus status);
    List<Approval> findBySubmittedById(Long userId);
}
