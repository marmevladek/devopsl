package ru.devopsl.backendservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devopsl.backendservice.model.Category;
import ru.devopsl.backendservice.payload.response.CategoryResponse;
import ru.devopsl.backendservice.payload.response.MessageResponse;
import ru.devopsl.backendservice.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createCategory(@RequestBody Category category) {
        logger.info("POST [/api/category/create] | Request received to add a new category");
        try {
            return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Error while adding product"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        logger.info("GET [/api/category/getAllCategories] | Request received to find all categories");
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

}
