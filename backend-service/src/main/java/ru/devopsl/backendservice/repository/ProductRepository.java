package ru.devopsl.backendservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devopsl.backendservice.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
