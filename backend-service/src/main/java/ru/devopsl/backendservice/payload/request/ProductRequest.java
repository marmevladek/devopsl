package ru.devopsl.backendservice.payload.request;

import jakarta.validation.constraints.*;

public record ProductRequest(

        @NotBlank(message = "Название не должно быть пустым") String name,

        @NotBlank(message = "Описание не должно быть пустым") String description,

        @NotBlank(message = "Полное описание не должно быть пустым") @Size(max = 1000, message = "Полное описание не должно превышать 1000 символов") String fullDescription,

        @Positive(message = "Цена должна быть положительным числом") float price,

        @NotBlank(message = "Ссылка на изображение обязательна") String linkImage,

        @NotBlank(message = "Номер телефона обязателен") @Pattern(regexp = "^\\+?[0-9\\-\\s]{7,15}$", message = "Неверный формат номера телефона") String phoneNumber,

        @NotBlank(message = "Email обязателен") @Email(message = "Неверный формат email") String email,

        @NotNull(message = "ID категории обязателен") Long category_id) {
}
