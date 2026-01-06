package com.kleberson.taskmanagerapi;

import com.kleberson.taskmanagerapi.security.JwtAuthFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TaskManagerApiApplicationTests {

    @MockBean
    JwtAuthFilter jwtAuthFilter;


    @Test
    void contextLoads() {
    }

}
