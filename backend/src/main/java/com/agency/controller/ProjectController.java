package com.agency.controller;

import com.agency.dto.EntityDtos;
import com.agency.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Projects", description = "Project management endpoints")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create new project", description = "Create a new project for a client")
    public ResponseEntity<EntityDtos.ProjectResponse> createProject(@Valid @RequestBody EntityDtos.ProjectRequest request) {
        EntityDtos.ProjectResponse response = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'DESIGNER', 'DEVELOPER')")
    @Operation(summary = "Get all projects", description = "Retrieve list of all projects")
    public ResponseEntity<List<EntityDtos.ProjectResponse>> getAllProjects() {
        List<EntityDtos.ProjectResponse> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'DESIGNER', 'DEVELOPER', 'CLIENT')")
    @Operation(summary = "Get project by ID", description = "Retrieve specific project details")
    public ResponseEntity<EntityDtos.ProjectResponse> getProject(@PathVariable Long id) {
        EntityDtos.ProjectResponse project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'DESIGNER', 'DEVELOPER')")
    @Operation(summary = "Get projects by client", description = "Retrieve all projects for a specific client")
    public ResponseEntity<List<EntityDtos.ProjectResponse>> getProjectsByClient(@PathVariable Long clientId) {
        List<EntityDtos.ProjectResponse> projects = projectService.getProjectsByClient(clientId);
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Update project", description = "Update project information")
    public ResponseEntity<EntityDtos.ProjectResponse> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody EntityDtos.ProjectRequest request) {
        EntityDtos.ProjectResponse response = projectService.updateProject(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete project", description = "Remove a project from the system")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
