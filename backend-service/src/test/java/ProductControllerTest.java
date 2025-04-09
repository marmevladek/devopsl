
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.devopsl.backendservice.BackendServiceApplication;
import ru.devopsl.backendservice.controller.ProductController;
import ru.devopsl.backendservice.payload.request.ProductRequest;
import ru.devopsl.backendservice.payload.response.MessageResponse;
import ru.devopsl.backendservice.payload.response.ProductResponse;
import ru.devopsl.backendservice.service.ProductService;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = BackendServiceApplication.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldAddProductSuccessfully() throws Exception {
        ProductRequest request = new ProductRequest("Футболка", "Краткое описание футболки", "Полное описание футболки",
                1234, "http://link.com/image", "+79999999999", "email@gmail.com", 1L);
        MessageResponse response = new MessageResponse("Product added");

        Mockito.when(productService.addProduct(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/product/create").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Product added"));
    }

    @Test
    void shouldUpdateProductSuccessfully() throws Exception {
        ProductRequest request = new ProductRequest("Футболка", "Краткое описание футболки", "Полное описание футболки",
                5421, "http://link.com/image", "+79324567876", "email@gmail.com", 1L);
        MessageResponse response = new MessageResponse("Product updated");

        Mockito.when(productService.updateProduct(Mockito.eq(1L), Mockito.any())).thenReturn(response);

        mockMvc.perform(put("/api/product/update/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Product updated"));
    }

    @Test
    void shouldReturnProductById() throws Exception {
        ProductResponse response = new ProductResponse(1L, "Футболка", "Краткое описание футболки",
                "Полное описание футболки", 1234, "http://link.com/image", "+79999999999", "email@gmail.com", "Одежда",
                LocalDateTime.now());

        Mockito.when(productService.getProductById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/product/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Футболка")).andExpect(jsonPath("$.price").value(1234));
    }

    @Test
    void shouldDeleteProductSuccessfully() throws Exception {
        MessageResponse response = new MessageResponse("Product deleted");

        Mockito.when(productService.deleteProduct(1L)).thenReturn(response);

        mockMvc.perform(delete("/api/product/delete/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Product deleted"));
    }
}