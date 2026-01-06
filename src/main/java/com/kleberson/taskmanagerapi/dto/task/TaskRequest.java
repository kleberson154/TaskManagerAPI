package com.kleberson.taskmanagerapi.dto.task;

public record TaskRequest(
        String title,
        String description
) {}

