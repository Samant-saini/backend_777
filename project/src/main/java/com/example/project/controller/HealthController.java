package com.example.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "running",
            "app", "PawSaver",
            "message", "Every paw counts 🐾"
        );
    }
    @GetMapping("/me")
public Map<String, String> me(Authentication authentication) {
    return Map.of(
        "loggedInAs", authentication.getName(),
        "role", authentication.getAuthorities().toString()
    );
}
}