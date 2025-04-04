package ru.devopsl.backendservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devopsl.backendservice.mapper.ProductMapper;
import ru.devopsl.backendservice.model.Product;
import ru.devopsl.backendservice.payload.request.ProductRequest;
import ru.devopsl.backendservice.payload.response.MessageResponse;
import ru.devopsl.backendservice.payload.response.ProductResponse;
import ru.devopsl.backendservice.repository.ProductRepository;
import ru.devopsl.backendservice.service.ProductService;
import ru.devopsl.backendservice.websocket.WebSocketHandler;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public MessageResponse addProduct(ProductRequest productRequest) {
        Product savedProduct = productRepository.save(ProductMapper.mapToProduct(productRequest));
        logger.info("ADD [addProduct()] | Product({}) has been successfully added", savedProduct.getId());

        return new MessageResponse("Product has been successfully added");
    }

    @Override
    public MessageResponse updateProduct(Long id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
                /*
                () -> new ProductNotFoundException("Product not found")
                 */
        );

        existingProduct.setName(productRequest.name());
        existingProduct.setDescription(productRequest.description());
        existingProduct.setPrice(productRequest.price());

        productRepository.save(existingProduct);
        logger.info("UPDATE [updateProduct()] | Product({}) has been successfully updated", id);

        return new MessageResponse("Product has been successfully updated");
    }

    @Override
    public MessageResponse deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
                /*
                () -> new ProductNotFoundException("Product not found")
                 */
        );

        productRepository.delete(existingProduct);
        logger.info("DELETE [deleteProduct()] | Product({}) has been successfully deleted", id);

        return new MessageResponse("Product has been successfully deleted");
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                /*
                () -> new ProductNotFoundException("Product not found")
                 */
        );
        logger.info("GET [getProductById()] | Product({}) has been successfully retrieved]", id);

        return ProductMapper.mapToProductResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::mapToProductResponse).collect(Collectors.toList());
    }
}
