import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.devopsl.backendservice.dto.ProductDTO;
import ru.devopsl.backendservice.model.Category;
import ru.devopsl.backendservice.model.Product;
import ru.devopsl.backendservice.payload.request.ProductRequest;
import ru.devopsl.backendservice.payload.response.MessageResponse;
import ru.devopsl.backendservice.payload.response.ProductResponse;
import ru.devopsl.backendservice.repository.CategoryRepository;
import ru.devopsl.backendservice.repository.ProductRepository;
import ru.devopsl.backendservice.service.impl.ProductServiceImpl;
import ru.devopsl.backendservice.websocket.WebSocketHandler;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock private WebSocketHandler webSocketHandler;
    @Mock private ProductRepository productRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private RabbitTemplate rabbitTemplate;

    @InjectMocks private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    private ProductRequest getSampleRequest() {
        return new ProductRequest("Product", "Desc", "FullDesc", 10.0f,
                "img.jpg", "1234567890", "test@mail.com", 1L);
    }

    private Product getSampleProduct(Category category) {
        Product product = new Product("Product", "Desc", "FullDesc", 10.0f,
                "img.jpg", "1234567890", "test@mail.com", category, LocalDateTime.now());
        ReflectionTestUtils.setField(product, "id", 1L);
        return product;
    }

    private Category getSampleCategory() {
        return new Category("Category");
    }

    @Test
    void testAddProduct_success() {
        ProductRequest request = getSampleRequest();
        Category category = getSampleCategory();
        Product product = getSampleProduct(category);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        MessageResponse response = productService.addProduct(request);

        assertEquals("Product has been successfully added", response.message());
        verify(webSocketHandler).sendProducts();
        verify(rabbitTemplate).convertAndSend(eq("match-exchange"), eq("match-exchange.create-product"), any(ProductDTO.class));
    }

    @Test
    void testAddProduct_categoryNotFound() {
        ProductRequest request = getSampleRequest();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.addProduct(request));
    }

    @Test
    void testUpdateProduct_success() {
        ProductRequest request = getSampleRequest();
        Category category = getSampleCategory();
        Product product = getSampleProduct(category);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        MessageResponse response = productService.updateProduct(1L, request);

        assertEquals("Product has been successfully updated", response.message());
        verify(webSocketHandler).sendProducts();
        verify(rabbitTemplate).convertAndSend(eq("match-exchange"), eq("match-exchange.update-product"), any(ProductDTO.class));
    }

    @Test
    void testDeleteProduct_success() {
        Category category = getSampleCategory();
        Product product = getSampleProduct(category);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        MessageResponse response = productService.deleteProduct(1L);

        assertEquals("Product has been successfully deleted", response.message());
        verify(productRepository).delete(product);
        verify(webSocketHandler).sendProducts();
        verify(rabbitTemplate).convertAndSend(eq("match-exchange"), eq("match-exchange.delete-product"), any(ProductDTO.class));
    }

    @Test
    void testGetProductById_success() {
        Category category = getSampleCategory();
        Product product = getSampleProduct(category);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertEquals(product.getId(), response.id());
        assertEquals(product.getName(), response.name());
    }

    @Test
    void testGetAllProducts() {
        Category category = getSampleCategory();
        Product product1 = getSampleProduct(category);
        Product product2 = getSampleProduct(category);
        ReflectionTestUtils.setField(product2, "id", 2L);

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        List<ProductResponse> responses = productService.getAllProducts();

        assertEquals(2, responses.size());
    }
}
