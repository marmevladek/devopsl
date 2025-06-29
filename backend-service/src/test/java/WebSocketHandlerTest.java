import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.devopsl.backendservice.model.Category;
import ru.devopsl.backendservice.model.Product;
import ru.devopsl.backendservice.repository.ProductRepository;
import ru.devopsl.backendservice.websocket.WebSocketHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WebSocketHandlerTest {

    private ProductRepository productRepository;
    private ObjectMapper objectMapper;
    private WebSocketHandler webSocketHandler;
    @Captor
    ArgumentCaptor<TextMessage> messageCaptor;

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

        ArgumentCaptor<TextMessage> captor = ArgumentCaptor.forClass(TextMessage.class);
        verify(session).sendMessage(captor.capture());

        String sentPayload = captor.getValue().getPayload();
        assertTrue(sentPayload.contains("Test"));
        assertTrue(sentPayload.contains("category"));
    }

    @Test
    void afterConnectionClosed_shouldRemoveSession() throws Exception {
        WebSocketSession session = mock(WebSocketSession.class);
        when(session.getId()).thenReturn("1");
        when(session.isOpen()).thenReturn(true);

        // Добавим соединение сначала
        Category category = new Category("category");
        Product product = new Product("Test", "Short", "Full", 10.0f, "img", "123", "mail@test.com", category,
                LocalDateTime.now());
        when(productRepository.findAll()).thenReturn(List.of(product));
        webSocketHandler.afterConnectionEstablished(session);

        // Закрываем соединение
        webSocketHandler.afterConnectionClosed(session, null);

        // Проверяем, что сессия удалена
        assertFalse(getSessions().contains(session));
        assertFalse(getLocks().containsKey(session.getId()));
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

    @SuppressWarnings("unchecked")
    private static Set<WebSocketSession> getSessions() throws Exception {
        var field = WebSocketHandler.class.getDeclaredField("sessions");
        field.setAccessible(true);
        return (Set<WebSocketSession>) field.get(null);
    }

    @SuppressWarnings("unchecked")
    private static ConcurrentMap<String, ReentrantLock> getLocks() throws Exception {
        var field = WebSocketHandler.class.getDeclaredField("SESSION_LOCKS");
        field.setAccessible(true);
        return (ConcurrentMap<String, ReentrantLock>) field.get(null);
    }
}
