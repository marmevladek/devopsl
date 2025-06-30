import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.test.util.ReflectionTestUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.devopsl.notificationservice.model.Notification;
import ru.devopsl.notificationservice.service.TelegramBotService;

class TelegramBotServiceTest {

    private TelegramBotService botSpy;

    @BeforeEach
    void setUp() throws TelegramApiException {
        botSpy = spy(new TelegramBotService("TEST_TOKEN"));

        doReturn(null).when(botSpy).execute(any(SendMessage.class));

        ReflectionTestUtils.setField(botSpy, "botName", "myTestBot");
    }

    @Test
    @DisplayName("getBotUsername() возвращает имя бота")
    void getBotUsername_returnsBotName() {
        assertEquals("myTestBot", botSpy.getBotUsername());
    }

    @Test
    @DisplayName("sendNotification() формирует корректный SendMessage и вызывает execute()")
    void sendNotification_executesSendMessage() throws Exception {
        long chatId = 42L;
        Notification notification = mock(Notification.class);
        when(notification.getText()).thenReturn("Hello!");

        botSpy.sendNotification(chatId, notification);

        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(botSpy).execute(captor.capture());

        SendMessage sent = captor.getValue();
        assertEquals(String.valueOf(chatId), sent.getChatId());
        assertEquals("Hello!", sent.getText());
    }

    @Test
    @DisplayName("executeMessage() логирует и подавляет TelegramApiException")
    void executeMessage_handlesException() throws Exception {
        doThrow(new TelegramApiException("Boom")).when(botSpy).execute(any(SendMessage.class));

        assertDoesNotThrow(() ->
                ReflectionTestUtils.invokeMethod(
                        botSpy,
                        "executeMessage",
                        SendMessage.builder().chatId(1L).text("test").build()
                )
        );
    }
}