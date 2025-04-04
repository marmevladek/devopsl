package ru.devopsl.backendservice.service;

import ru.devopsl.backendservice.model.Category;
import ru.devopsl.backendservice.payload.response.CategoryResponse;
import ru.devopsl.backendservice.payload.response.MessageResponse;

import java.util.List;

public interface CategoryService {
    MessageResponse createCategory(Category category);
    List<CategoryResponse> getAllCategories();
}
