package com.agency.service;

import com.agency.dto.EntityDtos;
import com.agency.entity.Client;
import com.agency.exception.ResourceNotFoundException;
import com.agency.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public EntityDtos.ClientResponse createClient(EntityDtos.ClientRequest request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Client client = Client.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .industry(request.getIndustry())
                .website(request.getWebsite())
                .build();

        client = clientRepository.save(client);
        log.info("Client created: {}", client.getId());
        return mapToResponse(client);
    }

    @Transactional(readOnly = true)
    public EntityDtos.ClientResponse getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        return mapToResponse(client);
    }

    @Transactional(readOnly = true)
    public List<EntityDtos.ClientResponse> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public EntityDtos.ClientResponse updateClient(Long id, EntityDtos.ClientRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setAddress(request.getAddress());
        client.setIndustry(request.getIndustry());
        client.setWebsite(request.getWebsite());

        client = clientRepository.save(client);
        log.info("Client updated: {}", id);
        return mapToResponse(client);
    }

    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client not found");
        }
        clientRepository.deleteById(id);
        log.info("Client deleted: {}", id);
    }

    private EntityDtos.ClientResponse mapToResponse(Client client) {
        return EntityDtos.ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .address(client.getAddress())
                .industry(client.getIndustry())
                .website(client.getWebsite())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }
}
