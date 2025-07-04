package ru.devopsl.backendservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.devopsl.backendservice.dto.ProductDTO;
import ru.devopsl.backendservice.mapper.ProductMapper;
import ru.devopsl.backendservice.model.Category;
import ru.devopsl.backendservice.model.Product;
import ru.devopsl.backendservice.payload.request.ProductRequest;
import ru.devopsl.backendservice.payload.response.MessageResponse;
import ru.devopsl.backendservice.payload.response.ProductResponse;
import ru.devopsl.backendservice.repository.CategoryRepository;
import ru.devopsl.backendservice.repository.ProductRepository;
import ru.devopsl.backendservice.service.ProductService;
import ru.devopsl.backendservice.websocket.WebSocketHandler;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final WebSocketHandler webSocketHandler;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final RabbitTemplate rabbitTemplate;

    public ProductServiceImpl(WebSocketHandler webSocketHandler, ProductRepository productRepository,
            CategoryRepository categoryRepository, RabbitTemplate rabbitTemplate) {
        this.webSocketHandler = webSocketHandler;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public MessageResponse addProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.category_id())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        logger.info("CREATE [addProduct()] | Category({}) exists", productRequest.category_id());

        Product savedProduct = productRepository.save(ProductMapper.mapToProduct(productRequest, category));
        logger.info("CREATE [addProduct()] | Product({}) has been successfully added", savedProduct.getId());

        webSocketHandler.sendProducts();

        rabbitTemplate.convertAndSend("match-exchange", "match-exchange.create-product",
                new ProductDTO(savedProduct.getId(), savedProduct.getName(), savedProduct.getDescription(),
                        savedProduct.getFullDescription(), savedProduct.getPrice(), savedProduct.getLinkImage(),
                        savedProduct.getPhoneNumber(), savedProduct.getEmail(), savedProduct.getCreatedAt(),
                        category.getName()));

        return new MessageResponse("Product has been successfully added");
    }

    @Override
    public MessageResponse updateProduct(Long id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
        /*
         * () -> new ProductNotFoundException("Product not found")
         */
        );

        Category category = categoryRepository.findById(productRequest.category_id()).orElseThrow();

        existingProduct.setName(productRequest.name());
        existingProduct.setDescription(productRequest.description());
        existingProduct.setPrice(productRequest.price());
        existingProduct.setCategory(category);

        productRepository.save(existingProduct);
        logger.info("UPDATE [updateProduct()] | Product({}) has been successfully updated", id);

        webSocketHandler.sendProducts();

        rabbitTemplate.convertAndSend("match-exchange", "match-exchange.update-product",
                new ProductDTO(existingProduct.getId(), existingProduct.getName(), existingProduct.getDescription(),
                        existingProduct.getFullDescription(), existingProduct.getPrice(),
                        existingProduct.getLinkImage(), existingProduct.getPhoneNumber(), existingProduct.getEmail(),
                        existingProduct.getCreatedAt(), existingProduct.getCategory().getName()));

        return new MessageResponse("Product has been successfully updated");
    }

    @Override
    public MessageResponse deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
        /*
         * () -> new ProductNotFoundException("Product not found")
         */
        );

        productRepository.delete(existingProduct);
        logger.info("DELETE [deleteProduct()] | Product({}) has been successfully deleted", id);

        webSocketHandler.sendProducts();

        rabbitTemplate.convertAndSend("match-exchange", "match-exchange.delete-product",
                new ProductDTO(existingProduct.getId(), existingProduct.getName(), existingProduct.getDescription(),
                        existingProduct.getFullDescription(), existingProduct.getPrice(),
                        existingProduct.getLinkImage(), existingProduct.getPhoneNumber(), existingProduct.getEmail(),
                        existingProduct.getCreatedAt(), existingProduct.getCategory().getName()));

        return new MessageResponse("Product has been successfully deleted");
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
        /*
         * () -> new ProductNotFoundException("Product not found")
         */
        );
        logger.info("GET [getProductById()] | Product({}) has been successfully retrieved]", id);
        String categoryName = product.getCategory().getName();
        return ProductMapper.mapToProductResponse(product, categoryName);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> ProductMapper.mapToProductResponse(product, product.getCategory().getName()))
                .collect(Collectors.toList());
    }
}
