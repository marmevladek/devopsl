package ru.devopsl.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private String fullDescription;

    private float price;

    private String linkImage;

    private String phoneNumber;

    private String email;

    private LocalDateTime createdAt;

    private String category;
}
