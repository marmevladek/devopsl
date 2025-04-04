package ru.devopsl.backendservice.mapper;

import ru.devopsl.backendservice.model.Product;
import ru.devopsl.backendservice.payload.request.ProductRequest;
import ru.devopsl.backendservice.payload.response.ProductResponse;

import java.time.LocalDateTime;

public class ProductMapper {

    public static Product mapToProduct(ProductRequest productRequest) {
        return new Product(
                productRequest.name(),
                productRequest.description(),
                productRequest.price(),
                productRequest.category(),
                LocalDateTime.now()
        );
    }

    public static ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getCreatedAt()
        );
    }
}
