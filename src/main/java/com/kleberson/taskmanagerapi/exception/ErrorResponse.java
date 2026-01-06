package com.kleberson.taskmanagerapi.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        int Status,
        String Error,
        String Message,
        LocalDateTime Timestamp
) {
}
