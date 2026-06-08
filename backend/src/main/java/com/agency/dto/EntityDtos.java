package com.agency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EntityDtos {

    // ========== CLIENT DTOs ==========
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClientRequest {
        @NotBlank(message = "Client name is required")
        private String name;

        @Email(message = "Email should be valid")
        private String email;

        private String phone;
        private String address;
        private String industry;
        private String website;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClientResponse {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private String industry;
        private String website;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // ========== PROJECT DTOs ==========
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProjectRequest {
        @NotNull(message = "Client ID is required")
        private Long clientId;

        @NotBlank(message = "Project name is required")
        private String name;

        private String description;
        private String status;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal budget;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProjectResponse {
        private Long id;
        private ClientResponse client;
        private String name;
        private String description;
        private String status;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal budget;
        private BigDecimal actualCost;
        private BigDecimal remainingBudget;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // ========== TASK DTOs ==========
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TaskRequest {
        @NotNull(message = "Project ID is required")
        private Long projectId;

        private Long assignedTo;

        @NotBlank(message = "Task title is required")
        private String title;

        private String description;
        private String status;
        private BigDecimal estimatedHours;
        private String priority;
        private LocalDate dueDate;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TaskResponse {
        private Long id;
        private Long projectId;
        private String projectName;
        private Long assignedToId;
        private String assignedToName;
        private String title;
        private String description;
        private String status;
        private BigDecimal estimatedHours;
        private BigDecimal actualHours;
        private String priority;
        private LocalDate dueDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // ========== TIME ENTRY DTOs ==========
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TimeEntryRequest {
        @NotNull(message = "User ID is required")
        private Long userId;

        @NotNull(message = "Project ID is required")
        private Long projectId;

        private Long taskId;

        @NotNull(message = "Entry date is required")
        private LocalDate entryDate;

        @NotNull(message = "Start time is required")
        private LocalDateTime startTime;

        private LocalDateTime endTime;
        private String description;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TimeEntryResponse {
        private Long id;
        private Long userId;
        private String userName;
        private Long projectId;
        private String projectName;
        private Long taskId;
        private String taskTitle;
        private LocalDate entryDate;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Integer durationMinutes;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // ========== INVOICE DTOs ==========
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InvoiceRequest {
        @NotNull(message = "Client ID is required")
        private Long clientId;

        private Long projectId;

        @NotBlank(message = "Invoice number is required")
        private String invoiceNumber;

        @NotNull(message = "Total amount is required")
        @DecimalMin("0.01")
        private BigDecimal totalAmount;

        private String status;

        @NotNull(message = "Due date is required")
        private LocalDate dueDate;

        private String notes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InvoiceResponse {
        private Long id;
        private ClientResponse client;
        private String projectName;
        private String invoiceNumber;
        private BigDecimal totalAmount;
        private String status;
        private LocalDate dueDate;
        private LocalDate paidDate;
        private String notes;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // ========== APPROVAL DTOs ==========
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ApprovalRequest {
        @NotNull(message = "Project ID is required")
        private Long projectId;

        private String type;
        private String submissionNotes;
        private String reviewerComments;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ApprovalResponse {
        private Long id;
        private Long projectId;
        private String projectName;
        private String type;
        private Long submittedById;
        private String submittedByName;
        private String status;
        private String submissionNotes;
        private String reviewerComments;
        private LocalDateTime submittedAt;
        private LocalDateTime reviewedAt;
        private LocalDateTime updatedAt;
    }

    // ========== PAGINATION REQUEST ==========
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PageRequest {
        @Min(0)
        private Integer page = 0;

        @Min(1)
        @Max(100)
        private Integer size = 20;

        private String sort;
        private String direction = "DESC";
    }

    // ========== PAGINATION RESPONSE ==========
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PageResponse<T> {
        private java.util.List<T> content;
        private Integer pageNumber;
        private Integer pageSize;
        private Long totalElements;
        private Integer totalPages;
        private Boolean isLast;
        private Boolean isFirst;
    }
}
