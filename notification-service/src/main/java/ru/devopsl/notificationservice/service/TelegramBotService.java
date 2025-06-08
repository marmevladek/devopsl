package ru.devopsl.notificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.devopsl.notificationservice.model.Notification;

@Service
public class TelegramBotService extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotService.class);

    @Value("${telegram.bot.name}")
    private String botName;

    public TelegramBotService(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }


    public void sendNotification(long chatId, Notification notification) {
        sendMessage(chatId, notification.getText());
    }

    private void sendMessage(long chatId, String text) {
        executeMessage(
                SendMessage.builder()
                        .chatId(chatId)
                        .text(text)
                        .build()
        );
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        }  catch (TelegramApiException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
