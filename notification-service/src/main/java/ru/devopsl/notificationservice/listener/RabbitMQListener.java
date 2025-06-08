package ru.devopsl.notificationservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.devopsl.notificationservice.dto.ProductDTO;
import ru.devopsl.notificationservice.model.Notification;
import ru.devopsl.notificationservice.service.TelegramBotService;

@Component
public class RabbitMQListener {

    private final TelegramBotService telegramBotService;

    @Value("${telegram.bot.chatId}")
    private Long chatId;

    public RabbitMQListener(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @RabbitListener(queues = "create-product-queue")
    public void handleCreateProduct(ProductDTO productDTO) {
        telegramBotService.sendNotification(
                chatId,
                new Notification(
                        "Добавлен новый товар:\n\n" +
                                "Идентификатор: " + productDTO.getId() + "\n" +
                                "Категория: " + productDTO.getCategory() + "\n" +
                                "Название: " + productDTO.getName() + "\n" +
                                "Описание: " + productDTO.getDescription() + "\n" +
                                "Цена: " + productDTO.getPrice() + "\n" +
                                "Номер телефона: " + productDTO.getPhoneNumber() + "\n" +
                                "Фото: " + productDTO.getLinkImage() + "\n" +
                                "Электронный адрес: " + productDTO.getEmail() + "\n" +
                                "Дата и время создания: " + productDTO.getCreatedAt()
                )
        );
    }

    @RabbitListener(queues = "update-product-queue")
    public void handleUpdateProduct(ProductDTO productDTO) {
        telegramBotService.sendNotification(
                chatId,
                new Notification(
                        "Товар обновлен (id=" + productDTO.getId() + "):\n\n" +
                                "Категория: " + productDTO.getCategory() + "\n" +
                                "Название: " + productDTO.getName() + "\n" +
                                "Описание: " + productDTO.getDescription() + "\n" +
                                "Цена: " + productDTO.getPrice() + "\n" +
                                "Номер телефона: " + productDTO.getPhoneNumber() + "\n" +
                                "Фото: " + productDTO.getLinkImage() + "\n" +
                                "Электронный адрес: " + productDTO.getEmail() + "\n" +
                                "Дата и время создания: " + productDTO.getCreatedAt()
                )
        );
    }

    @RabbitListener(queues = "delete-product-queue")
    public void handleDeleteProduct(ProductDTO productDTO) {
        telegramBotService.sendNotification(
                chatId,
                new Notification(
                        "Товар удален (" + productDTO.getId() + "):\n\n" +
                                "Категория: " + productDTO.getCategory() + "\n" +
                                "Название: " + productDTO.getName() + "\n" +
                                "Описание: " + productDTO.getDescription() + "\n" +
                                "Цена: " + productDTO.getPrice() + "\n" +
                                "Номер телефона: " + productDTO.getPhoneNumber() + "\n" +
                                "Фото: " + productDTO.getLinkImage() + "\n" +
                                "Электронный адрес: " + productDTO.getEmail() + "\n" +
                                "Дата и время создания: " + productDTO.getCreatedAt()
                )
        );
    }
}
