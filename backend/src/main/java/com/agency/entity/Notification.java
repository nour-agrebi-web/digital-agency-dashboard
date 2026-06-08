package com.agency.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    private Long relatedId;

    @Column(name = "related_type")
    private String relatedType;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "read_status", nullable = false)
    private Boolean readStatus = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum NotificationType {
        ASSIGNMENT,
        APPROVAL,
        MENTION,
        UPDATE,
        CHAT,
        INVOICE,
        DEADLINE
    }
}
