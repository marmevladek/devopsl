package ru.devopsl.backendservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.devopsl.backendservice.repository.ProductRepository;
import ru.devopsl.backendservice.service.ProductService;
import ru.devopsl.backendservice.websocket.WebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public WebSocketConfig(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(productRepository, objectMapper), "/ws").setAllowedOrigins("*");
    }
}
