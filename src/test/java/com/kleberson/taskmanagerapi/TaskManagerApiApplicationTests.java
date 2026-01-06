package com.kleberson.taskmanagerapi;

import com.kleberson.taskmanagerapi.config.TestSecurityConfig;
import com.kleberson.taskmanagerapi.security.JwtAuthFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
class TaskManagerApiApplicationTests {

    @MockBean
    JwtAuthFilter jwtAuthFilter;


    @Test
    void contextLoads() {
    }

}
