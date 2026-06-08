package com.agency.repository;

import com.agency.entity.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    List<TimeEntry> findByUserId(Long userId);
    List<TimeEntry> findByProjectId(Long projectId);
    List<TimeEntry> findByEntryDate(LocalDate date);
    List<TimeEntry> findByUserIdAndEntryDateBetween(Long userId, LocalDate start, LocalDate end);
}
