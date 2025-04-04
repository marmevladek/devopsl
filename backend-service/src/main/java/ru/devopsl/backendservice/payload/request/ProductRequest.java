package ru.devopsl.backendservice.payload.request;


public record ProductRequest(
        String name,
        String description,
        float price,
        Long category_id
) {}
