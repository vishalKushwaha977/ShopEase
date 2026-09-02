package com.shopease.product_service.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shopease.product_service.dto.ProductRequestDTO;
import com.shopease.product_service.dto.ProductResponseDTO;
import com.shopease.product_service.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    public final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    
    /**
     * Creates a new product.
     *
     * @param request product details
     * @return the newly created product with HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody  @Valid ProductRequestDTO request) {
        ProductResponseDTO response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id product identifier
     * @return product details with HTTP 200 OK,
     *         or HTTP 404 Not Found if the product does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @PathVariable Long id) {

        Optional<ProductResponseDTO> product =
                productService.getProductById(id);

        return product
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    /**
     * Retrieves all products.
     *
     * @return list of all products with HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    
    
    /**
     * Updates an existing product.
     *
     * @param id product identifier
     * @param request updated product details
     * @return updated product with HTTP 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct( @PathVariable  @Valid Long id,@RequestBody ProductRequestDTO request) {

        ProductResponseDTO response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
