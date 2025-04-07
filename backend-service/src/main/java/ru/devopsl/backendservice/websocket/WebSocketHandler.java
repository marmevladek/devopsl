package ru.devopsl.backendservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.devopsl.backendservice.payload.response.ProductResponse;
import ru.devopsl.backendservice.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    public WebSocketHandler(ProductService productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        sessions.add(session);

        List<ProductResponse> products = productService.getAllProducts();
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
    }

    @Scheduled(fixedDelay = 1000)
    public void sendProducts() {
        try {
            List<ProductResponse> products = productService.getAllProducts();
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
