package com.kleberson.taskmanagerapi.dto.task;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        String status,
        LocalDateTime createdAt
) {}

