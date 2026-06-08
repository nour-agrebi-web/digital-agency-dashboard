package com.agency.service;

import com.agency.dto.AuthDto;
import com.agency.entity.User;
import com.agency.exception.ResourceNotFoundException;
import com.agency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public AuthDto.UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return AuthDto.UserDTO.fromUser(user);
    }

    @Transactional(readOnly = true)
    public AuthDto.UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return AuthDto.UserDTO.fromUser(user);
    }

    @Transactional
    public AuthDto.UserDTO updateUser(Long id, AuthDto.UserDTO updateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (updateRequest.getFirstName() != null) {
            user.setFirstName(updateRequest.getFirstName());
        }
        if (updateRequest.getLastName() != null) {
            user.setLastName(updateRequest.getLastName());
        }
        if (updateRequest.getDepartment() != null) {
            user.setDepartment(updateRequest.getDepartment());
        }

        user = userRepository.save(user);
        return AuthDto.UserDTO.fromUser(user);
    }
}
