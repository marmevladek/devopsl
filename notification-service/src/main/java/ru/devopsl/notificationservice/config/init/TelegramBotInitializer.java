package ru.devopsl.notificationservice.config.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.devopsl.notificationservice.service.TelegramBotService;

@Configuration
public class TelegramBotInitializer {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotInitializer.class);

    private final TelegramBotService telegramBotService;

    public TelegramBotInitializer(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            telegramBotsApi.registerBot(telegramBotService);
        } catch (Exception e) {
            logger.error("Telegram bot initialization failed: {}", e.getMessage());
        }
    }
}
