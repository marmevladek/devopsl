package ru.devopsl.backendservice.service;

import ru.devopsl.backendservice.payload.request.ProductRequest;
import ru.devopsl.backendservice.payload.response.MessageResponse;
import ru.devopsl.backendservice.payload.response.ProductResponse;

import java.util.List;

public interface ProductService {

    MessageResponse addProduct(ProductRequest productRequest);
    MessageResponse updateProduct(Long id, ProductRequest productRequest);
    MessageResponse deleteProduct(Long id);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
}
