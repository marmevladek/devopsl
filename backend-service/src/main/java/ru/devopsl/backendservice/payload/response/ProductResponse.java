package ru.devopsl.backendservice.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ProductResponse(Long id, String name, String description, String fullDescription, float price,
        String linkImage, String phoneNumber, String email, String category,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt) {
}
