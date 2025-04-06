package ru.devopsl.backendservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devopsl.backendservice.payload.request.ProductRequest;
import ru.devopsl.backendservice.payload.response.MessageResponse;
import ru.devopsl.backendservice.payload.response.ProductResponse;
import ru.devopsl.backendservice.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> addProduct(@RequestBody ProductRequest productRequest) {
        logger.info("POST [/api/product/create] | Request received to add a new product");
        try {
            return new ResponseEntity<>(productService.addProduct(productRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Error while adding product"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponse> updateProduct(@PathVariable Long id,
            @RequestBody ProductRequest productRequest) {
        logger.info("PUT [/api/product/update] | Request for product({}) change received", id);
        try {
            return new ResponseEntity<>(productService.updateProduct(id, productRequest), HttpStatus.OK);
        }
        /*
         *
         * catch (ProductNotFoundException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new
         * MessageResponse("Product not found")); }
         *
         */
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error updating product"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        logger.info("GET [/api/product/get] | Request for product({}) received", id);
        try {
            ProductResponse productResponse = productService.getProductById(id);
            return ResponseEntity.ok(productResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        /*
         * catch (ProductNotFoundException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); }
         *
         */
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable Long id) {
        logger.info("DELETE [/api/product/delete] | Request to remove product({}) received", id);
        try {
            return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
        }
        /*
         *
         * catch (ProductNotFoundException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new
         * MessageResponse("Product not found")); }
         *
         */
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error deleting product"));
        }
    }

}
