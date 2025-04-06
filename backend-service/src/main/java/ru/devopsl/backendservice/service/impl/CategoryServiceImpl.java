package ru.devopsl.backendservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devopsl.backendservice.mapper.CategoryMapper;
import ru.devopsl.backendservice.model.Category;
import ru.devopsl.backendservice.payload.response.CategoryResponse;
import ru.devopsl.backendservice.payload.response.MessageResponse;
import ru.devopsl.backendservice.repository.CategoryRepository;
import ru.devopsl.backendservice.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MessageResponse createCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        logger.info("CREATE [createCategory()] | Category({}) has been successfully created]", savedCategory.getId());

        return new MessageResponse("Category has been successfully created");
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryMapper::mapToCategoryResponse)
                .collect(Collectors.toList());
    }
}
