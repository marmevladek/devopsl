package ru.devopsl.backendservice.service;

import ru.devopsl.backendservice.model.Product;
import ru.devopsl.backendservice.payload.response.MessageResponse;
import ru.devopsl.backendservice.payload.response.ProductResponse;

public interface ProductService {

    MessageResponse addProduct(Product product);
    MessageResponse updateProduct(Long id, Product product);
    MessageResponse deleteProduct(Long id);
    ProductResponse getProductById(Long id);
}
