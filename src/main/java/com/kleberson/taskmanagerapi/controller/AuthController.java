package com.kleberson.taskmanagerapi.controller;

import com.kleberson.taskmanagerapi.dto.auth.AuthResponse;
import com.kleberson.taskmanagerapi.dto.auth.LoginRequest;
import com.kleberson.taskmanagerapi.dto.auth.RegisterRequest;
import com.kleberson.taskmanagerapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}

