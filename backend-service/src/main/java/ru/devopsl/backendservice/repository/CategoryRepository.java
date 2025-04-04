package ru.devopsl.backendservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devopsl.backendservice.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
