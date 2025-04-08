package ru.devopsl.backendservice.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.devopsl.backendservice.mapper.ProductMapper;
import ru.devopsl.backendservice.payload.response.ProductResponse;
import ru.devopsl.backendservice.repository.ProductRepository;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public WebSocketHandler(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        sessions.add(session);
        logger.info("WEBSOCKET | New connection established");

        List<ProductResponse> products = productRepository.findAll().stream()
                .map(product -> ProductMapper.mapToProductResponse(product, product.getCategory().getName()))
                .collect(Collectors.toList());
        String jsonResponse = objectMapper.writeValueAsString(products);

        synchronized (session) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(jsonResponse));
                logger.info("WEBSOCKET [afterConnectionEstablished()] | Products sent on connection");
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
        logger.info("WEBSOCKET | Connection closed");
    }

    @Scheduled(fixedDelay = 1000)
    public void sendProducts() {
        try {
            List<ProductResponse> products = productRepository.findAll().stream()
                    .map(product -> ProductMapper.mapToProductResponse(product, product.getCategory().getName()))
                    .collect(Collectors.toList());
            String jsonResponse = objectMapper.writeValueAsString(products);

            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(jsonResponse));
                    logger.info("WEBSOCKET [sendProducts()] | List of all products sent");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
