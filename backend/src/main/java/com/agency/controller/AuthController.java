package com.agency.controller;

import com.agency.dto.AuthDto;
import com.agency.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User authentication endpoints")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account with email, password, and role")
    public ResponseEntity<AuthDto.JwtResponse> register(@Valid @RequestBody AuthDto.RegisterRequest request) {
        AuthDto.JwtResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT tokens")
    public ResponseEntity<AuthDto.JwtResponse> login(@Valid @RequestBody AuthDto.LoginRequest request) {
        AuthDto.JwtResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token", description = "Generate new access token using refresh token")
    public ResponseEntity<AuthDto.JwtResponse> refreshToken(@Valid @RequestBody AuthDto.RefreshRequest request) {
        AuthDto.JwtResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Retrieve authenticated user information")
    public ResponseEntity<AuthDto.UserDTO> getCurrentUser() {
        String email = (String) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        AuthDto.UserDTO user = authService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "Logout user (client-side token deletion)")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out successfully");
    }
}
