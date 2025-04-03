package ru.devopsl.backendservice.service.impl;

import org.springframework.stereotype.Service;
import ru.devopsl.backendservice.mapper.ProductMapper;
import ru.devopsl.backendservice.model.Product;
import ru.devopsl.backendservice.payload.request.ProductRequest;
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
    public MessageResponse addProduct(ProductRequest productRequest) {
        productRepository.save(ProductMapper.mapToProduct(productRequest));
        return new MessageResponse("Product has been successfully added");
    }

    @Override
    public MessageResponse updateProduct(Long id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
                /*
                () -> new ProductNotFoundException("Product not found")
                 */
        );

        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());

        productRepository.save(existingProduct);

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
        return new MessageResponse("Product has been successfully deleted");
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                /*
                () -> new ProductNotFoundException("Product not found")
                 */
        );

        return ProductMapper.mapToProductResponse(product);
    }
}
