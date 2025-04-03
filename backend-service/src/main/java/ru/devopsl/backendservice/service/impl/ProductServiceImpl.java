package ru.devopsl.backendservice.service.impl;

import org.springframework.stereotype.Service;
import ru.devopsl.backendservice.model.Product;
import ru.devopsl.backendservice.payload.response.MessageResponse;
import ru.devopsl.backendservice.payload.response.ProductResponse;
import ru.devopsl.backendservice.repository.ProductRepository;
import ru.devopsl.backendservice.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public MessageResponse addProduct(Product product) {
        return null;
    }

    @Override
    public MessageResponse updateProduct(Product product) {
        return null;
    }

    @Override
    public MessageResponse deleteProduct(Long id) {
        return null;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return null;
    }
}
