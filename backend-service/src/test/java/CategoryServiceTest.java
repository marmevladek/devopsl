import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import ru.devopsl.backendservice.model.Category;
import ru.devopsl.backendservice.payload.response.CategoryResponse;
import ru.devopsl.backendservice.payload.response.MessageResponse;
import ru.devopsl.backendservice.repository.CategoryRepository;
import ru.devopsl.backendservice.service.impl.CategoryServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /** Вспомогательный метод для создания Category с нужным id. */
    private Category buildCategory(Long id, String name) {
        Category category = new Category();
        category.setName(name);
        ReflectionTestUtils.setField(category, "id", id);
        return category;
    }

    @Test
    @DisplayName("createCategory() сохраняет категорию и возвращает MessageResponse")
    void createCategory_success() {
        Category incoming = buildCategory(null, "Electronics");
        Category saved    = buildCategory(1L,  "Electronics");

        when(categoryRepository.save(any(Category.class))).thenReturn(saved);

        MessageResponse response = categoryService.createCategory(incoming);

        assertEquals("Category has been successfully created", response.message());
        verify(categoryRepository).save(incoming);
    }

    @Test
    @DisplayName("getAllCategories() мапирует и возвращает все категории")
    void getAllCategories_success() {
        Category cat1 = buildCategory(1L, "Electronics");
        Category cat2 = buildCategory(2L, "Home");

        when(categoryRepository.findAll()).thenReturn(List.of(cat1, cat2));

        List<CategoryResponse> responses = categoryService.getAllCategories();

        assertEquals(2, responses.size());
        assertEquals(cat1.getId(), responses.get(0).id());
        assertEquals(cat1.getName(), responses.get(0).name());
        assertEquals(cat2.getId(), responses.get(1).id());
        assertEquals(cat2.getName(), responses.get(1).name());

        verify(categoryRepository).findAll();
    }
}