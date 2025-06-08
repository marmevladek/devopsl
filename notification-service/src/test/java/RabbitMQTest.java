import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import ru.devopsl.notificationservice.dto.ProductDTO;
import ru.devopsl.notificationservice.service.TelegramBotService;
import ru.devopsl.notificationservice.model.Notification;
import ru.devopsl.notificationservice.listener.RabbitMQListener;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RabbitMQTest {

    private TelegramBotService telegramBotService;
    private RabbitMQListener rabbitMQListener;

    private final Long CHAT_ID = 12323546L;

    @BeforeEach
    void setUp() {
        telegramBotService = mock(TelegramBotService.class);
        rabbitMQListener = new RabbitMQListener(telegramBotService);
        ReflectionTestUtils.setField(rabbitMQListener, "chatId", CHAT_ID);
    }

    private ProductDTO getSampleProduct() {
        ProductDTO dto = new ProductDTO();
        dto.setId(1L);
        dto.setCategory("Электроника");
        dto.setName("Смартфон");
        dto.setDescription("Новый смартфон 2025");
        dto.setPrice(999.99F);
        dto.setPhoneNumber("+79001234567");
        dto.setLinkImage("http://image.url");
        dto.setEmail("example@example.com");
        dto.setCreatedAt(LocalDateTime.now());
        return dto;
    }

    @Test
    void testHandleCreateProduct() {
        ProductDTO product = getSampleProduct();

        rabbitMQListener.handleCreateProduct(product);

        verifyNotificationSent("Добавлен новый товар:", product);
    }

    @Test
    void testHandleUpdateProduct() {
        ProductDTO product = getSampleProduct();

        rabbitMQListener.handleUpdateProduct(product);

        verifyNotificationSent("Товар обновлен (id=1):", product);
    }

    @Test
    void testHandleDeleteProduct() {
        ProductDTO product = getSampleProduct();

        rabbitMQListener.handleDeleteProduct(product);

        verifyNotificationSent("Товар удален (1):", product);
    }

    private void verifyNotificationSent(String prefix, ProductDTO product) {
        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(telegramBotService).sendNotification(eq(CHAT_ID), captor.capture());

        Notification sentNotification = captor.getValue();
        assertNotNull(sentNotification);
        String text = sentNotification.getText();
        assertTrue(text.startsWith(prefix));
        assertTrue(text.contains(product.getName()));
        assertTrue(text.contains(product.getEmail()));
    }
}