package com.agency.service;

import com.agency.dto.EntityDtos;
import com.agency.entity.Client;
import com.agency.entity.Project;
import com.agency.exception.ResourceNotFoundException;
import com.agency.repository.ClientRepository;
import com.agency.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public EntityDtos.ProjectResponse createProject(EntityDtos.ProjectRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        Project project = Project.builder()
                .client(client)
                .name(request.getName())
                .description(request.getDescription())
                .status(Project.ProjectStatus.valueOf(request.getStatus() != null ? request.getStatus() : "PLANNING"))
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .budget(request.getBudget())
                .build();

        project = projectRepository.save(project);
        log.info("Project created: {}", project.getId());
        return mapToResponse(project);
    }

    @Transactional(readOnly = true)
    public EntityDtos.ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return mapToResponse(project);
    }

    @Transactional(readOnly = true)
    public List<EntityDtos.ProjectResponse> getProjectsByClient(Long clientId) {
        return projectRepository.findByClientId(clientId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EntityDtos.ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public EntityDtos.ProjectResponse updateProject(Long id, EntityDtos.ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            project.setStatus(Project.ProjectStatus.valueOf(request.getStatus()));
        }
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setBudget(request.getBudget());

        project = projectRepository.save(project);
        log.info("Project updated: {}", id);
        return mapToResponse(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found");
        }
        projectRepository.deleteById(id);
        log.info("Project deleted: {}", id);
    }

    private EntityDtos.ProjectResponse mapToResponse(Project project) {
        EntityDtos.ClientResponse clientResponse = EntityDtos.ClientResponse.builder()
                .id(project.getClient().getId())
                .name(project.getClient().getName())
                .email(project.getClient().getEmail())
                .build();

        return EntityDtos.ProjectResponse.builder()
                .id(project.getId())
                .client(clientResponse)
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus().toString())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .budget(project.getBudget())
                .actualCost(project.getActualCost())
                .remainingBudget(project.getRemainingBudget())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
