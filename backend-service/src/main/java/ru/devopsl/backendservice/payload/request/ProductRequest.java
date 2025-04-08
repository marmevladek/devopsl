package ru.devopsl.backendservice.payload.request;

public record ProductRequest(String name, String description, String fullDescription, float price, String linkImage,
        String phoneNumber, String email, Long category_id) {
}
