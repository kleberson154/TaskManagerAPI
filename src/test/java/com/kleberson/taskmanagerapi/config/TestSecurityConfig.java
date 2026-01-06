package com.kleberson.taskmanagerapi.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@TestConfiguration
public class TestSecurityConfig {
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new DaoAuthenticationProvider();
    }
}
