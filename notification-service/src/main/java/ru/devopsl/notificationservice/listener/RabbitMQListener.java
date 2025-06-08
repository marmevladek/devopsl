package ru.devopsl.notificationservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.devopsl.notificationservice.dto.ProductDTO;
import ru.devopsl.notificationservice.service.TelegramBotService;

@Component
public class RabbitMQListener {
    private final TelegramBotService telegramBotService;

    public RabbitMQListener(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @RabbitListener(queues = "create-product-queue")
    public void handleCreateProduct(ProductDTO productDTO) {
    }

    @RabbitListener(queues = "update-product-queue")
    public void handleUpdateProduct(ProductDTO productDTO) {

    }

    @RabbitListener(queues = "delete-product-queue")
    public void handleDeleteProduct(ProductDTO productDTO) {

    }
}
