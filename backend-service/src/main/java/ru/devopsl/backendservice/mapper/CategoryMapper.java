package ru.devopsl.backendservice.mapper;

import ru.devopsl.backendservice.model.Category;
import ru.devopsl.backendservice.payload.response.CategoryResponse;

public class CategoryMapper {

    public static Category mapToCategory(CategoryResponse categoryResponse) {
        return new Category(categoryResponse.name());
    }

    public static CategoryResponse mapToCategoryResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
