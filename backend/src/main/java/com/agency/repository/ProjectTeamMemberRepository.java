package com.agency.repository;

import com.agency.entity.ProjectTeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectTeamMemberRepository extends JpaRepository<ProjectTeamMember, Long> {
    List<ProjectTeamMember> findByProjectId(Long projectId);
    List<ProjectTeamMember> findByUserId(Long userId);
}
