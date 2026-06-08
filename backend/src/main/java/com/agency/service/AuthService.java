package com.agency.service;

import com.agency.config.JwtTokenProvider;
import com.agency.dto.AuthDto;
import com.agency.entity.User;
import com.agency.exception.ResourceNotFoundException;
import com.agency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthDto.JwtResponse register(AuthDto.RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(User.UserRole.valueOf(request.getRole().toUpperCase()))
                .department(request.getDepartment())
                .active(true)
                .build();

        user = userRepository.save(user);
        log.info("User registered: {}", user.getEmail());

        return generateTokenResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthDto.JwtResponse login(AuthDto.LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getActive()) {
            throw new IllegalArgumentException("User account is inactive");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        log.info("User logged in: {}", user.getEmail());
        return generateTokenResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthDto.JwtResponse refreshToken(AuthDto.RefreshRequest request) {
        String token = request.getRefreshToken();

        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }

        String email = jwtTokenProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getActive()) {
            throw new IllegalArgumentException("User account is inactive");
        }

        log.info("Token refreshed for user: {}", user.getEmail());
        return generateTokenResponse(user);
    }

    private AuthDto.JwtResponse generateTokenResponse(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), user.getRole().toString());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return AuthDto.JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(900L)
                .user(AuthDto.UserDTO.fromUser(user))
                .build();
    }

    @Transactional(readOnly = true)
    public AuthDto.UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return AuthDto.UserDTO.fromUser(user);
    }
}
