package com.example.project.services;

import com.example.project.dto.AuthResponse;
import com.example.project.dto.LoginRequest;
import com.example.project.dto.RegisterRequest;

import com.example.project.entity.User;

import com.example.project.Repository.UserRepository;

import com.example.project.security.JwtUtil;

import com.example.project.exception.BadRequestException;
import com.example.project.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // REGISTER METHOD
    public AuthResponse register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {

            throw new BadRequestException(
                    "Email already registered"
            );
        }

        // Create user
        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        // Encrypt password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(
                User.Role.valueOf(
                        request.getRole().toUpperCase()
                )
        );

        // Save user
        userRepository.save(user);

        return new AuthResponse(
                "Registration successful",
                user.getEmail(),
                user.getRole().name(),
                null
        );
    }

    // LOGIN METHOD
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(
                request.getEmail()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "User not found"
                )
        );

        // Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new BadRequestException(
                    "Invalid password"
            );
        }

        // Generate JWT Token
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(
                "Login successful",
                user.getEmail(),
                user.getRole().name(),
                token
        );
    }
}