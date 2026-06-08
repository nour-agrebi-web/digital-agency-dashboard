package com.agency.controller;

import com.agency.dto.EntityDtos;
import com.agency.service.ClientService;
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
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Client management endpoints")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create new client", description = "Add a new client to the system")
    public ResponseEntity<EntityDtos.ClientResponse> createClient(@Valid @RequestBody EntityDtos.ClientRequest request) {
        EntityDtos.ClientResponse response = clientService.createClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'DESIGNER', 'DEVELOPER')")
    @Operation(summary = "Get all clients", description = "Retrieve list of all clients")
    public ResponseEntity<List<EntityDtos.ClientResponse>> getAllClients() {
        List<EntityDtos.ClientResponse> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'DESIGNER', 'DEVELOPER', 'CLIENT')")
    @Operation(summary = "Get client by ID", description = "Retrieve specific client details")
    public ResponseEntity<EntityDtos.ClientResponse> getClient(@PathVariable Long id) {
        EntityDtos.ClientResponse client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Update client", description = "Update client information")
    public ResponseEntity<EntityDtos.ClientResponse> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody EntityDtos.ClientRequest request) {
        EntityDtos.ClientResponse response = clientService.updateClient(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete client", description = "Remove a client from the system")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
