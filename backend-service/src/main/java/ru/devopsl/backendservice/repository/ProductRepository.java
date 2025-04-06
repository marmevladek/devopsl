package ru.devopsl.backendservice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devopsl.backendservice.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = { "category" })
    List<Product> findAll();

    @EntityGraph(attributePaths = { "category" })
    Optional<Product> findById(Long id);
}
