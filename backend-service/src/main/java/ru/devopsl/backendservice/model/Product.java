package ru.devopsl.backendservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(name = "description")
    private String description;

    @Column(name = "full_description")
    private String fullDescription;

    @Column(name = "price")
    private float price;

    @Column(name = "link_image")
    private String linkImage;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    public Product(String name, String description, String fullDescription, float price, String linkImage,
            String phoneNumber, String email, Category category, LocalDateTime createdAt) {
        this.name = name;
        this.description = description;
        this.fullDescription = fullDescription;
        this.price = price;
        this.linkImage = linkImage;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.category = category;
        this.createdAt = createdAt;
    }
}
