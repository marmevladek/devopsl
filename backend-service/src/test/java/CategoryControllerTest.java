package ru.devopsl.backendservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.devopsl.backendservice.model.Category;
import ru.devopsl.backendservice.payload.response.CategoryResponse;
import ru.devopsl.backendservice.payload.response.MessageResponse;
import ru.devopsl.backendservice.service.CategoryService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @Test
    @DisplayName("POST /api/category/create returns 201 and success message")
    void createCategory_success() throws Exception {
        MessageResponse expected = new MessageResponse("Category has been successfully added");
        when(categoryService.createCategory(any(Category.class))).thenReturn(expected);

        String requestBody = """
                {
                  "name": "Electronics"
                }
                """;

        mockMvc.perform(post("/api/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Category has been successfully added"));

        verify(categoryService).createCategory(any(Category.class));
    }

    @Test
    @DisplayName("POST /api/category/create returns 400 when service throws")
    void createCategory_failure() throws Exception {
        when(categoryService.createCategory(any(Category.class)))
                .thenThrow(new RuntimeException("DB error"));

        String requestBody = """
                {
                  "name": "Electronics"
                }
                """;

        mockMvc.perform(post("/api/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error while adding product"));
    }

    @Test
    @DisplayName("GET /api/category/getAllCategories returns 200 and list of categories")
    void getAllCategories_success() throws Exception {
        List<CategoryResponse> responses = List.of(
                new CategoryResponse(1L, "Electronics"),
                new CategoryResponse(2L, "Home")
        );
        when(categoryService.getAllCategories()).thenReturn(responses);

        mockMvc.perform(get("/api/category/getAllCategories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Electronics"))
                .andExpect(jsonPath("$[1].name").value("Home"));

        verify(categoryService).getAllCategories();
    }
}
