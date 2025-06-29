package ru.devopsl.backendservice.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.devopsl.backendservice.model.Category;
import ru.devopsl.backendservice.model.Product;
import ru.devopsl.backendservice.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WebSocketHandlerTest {

    private ProductRepository productRepository;
    private ObjectMapper objectMapper;
    private WebSocketHandler webSocketHandler;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // <-- добавь это
        webSocketHandler = new WebSocketHandler(productRepository, objectMapper);
    }

    @Test
    void afterConnectionEstablished_shouldSendProducts() throws Exception {
        WebSocketSession session = mock(WebSocketSession.class);
        when(session.getId()).thenReturn("1");
        when(session.isOpen()).thenReturn(true);

        Category category = new Category("category");
        Product product = new Product("Test", "Short", "Full", 10.0f, "img", "123", "mail@test.com", category,
                LocalDateTime.now());

        when(productRepository.findAll()).thenReturn(List.of(product));

        webSocketHandler.afterConnectionEstablished(session);

        verify(session).sendMessage(any(TextMessage.class));
    }

    @Test
    void afterConnectionClosed_shouldRemoveSession() {
        WebSocketSession session = mock(WebSocketSession.class);
        when(session.getId()).thenReturn("1");

        webSocketHandler.afterConnectionClosed(session, null);

        // No exception = pass
    }

    @Test
    void sendProducts_shouldSendToOpenSessionsOnly() throws Exception {
        WebSocketSession session1 = mock(WebSocketSession.class);
        when(session1.getId()).thenReturn("1");
        when(session1.isOpen()).thenReturn(true);

        WebSocketSession session2 = mock(WebSocketSession.class);
        when(session2.getId()).thenReturn("2");
        when(session2.isOpen()).thenReturn(false);

        Category category = new Category("category");
        Product product = new Product("Test", "Short", "Full", 10.0f, "img", "123", "mail@test.com", category,
                LocalDateTime.now());
        when(productRepository.findAll()).thenReturn(List.of(product));

        webSocketHandler.afterConnectionEstablished(session1);
        webSocketHandler.afterConnectionEstablished(session2);

        webSocketHandler.sendProducts();

        verify(session1, atLeastOnce()).sendMessage(any(TextMessage.class));
        verify(session2, never()).sendMessage(any());
    }
}
