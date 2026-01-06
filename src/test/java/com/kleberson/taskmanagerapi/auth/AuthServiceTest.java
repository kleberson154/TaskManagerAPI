package com.kleberson.taskmanagerapi.auth;

import com.kleberson.taskmanagerapi.dto.auth.AuthResponse;
import com.kleberson.taskmanagerapi.dto.auth.LoginRequest;
import com.kleberson.taskmanagerapi.dto.auth.RegisterRequest;
import com.kleberson.taskmanagerapi.model.User;
import com.kleberson.taskmanagerapi.repository.UserRepository;
import com.kleberson.taskmanagerapi.security.JwtService;
import com.kleberson.taskmanagerapi.service.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerUser_shouldSaveUser() {
        RegisterRequest request = new RegisterRequest("Teste", "teste@email.com", "123456");

        Mockito.when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        Mockito.when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("token123");

        AuthResponse result = authService.register(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("token123", result.token());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    void authenticateUser_shouldReturnToken() {
        LoginRequest request = new LoginRequest();
        String email = "teste@email.com";
        request.setEmail(email);
        request.setPassword("123456");

        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(jwtService.generateToken(user)).thenReturn("token123");

        String token = authService.login(request).token();

        Assertions.assertEquals("token123", token);
    }
}

