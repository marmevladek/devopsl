package ru.devopsl.backendservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "desctiption")
    private String description;

    @Column(name = "price")
    private float price;

    @Column(name = "category")
    private String category;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    public Product(String name, String description, float price, String category, LocalDateTime createdAt) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.createdAt = createdAt;
    }
}
