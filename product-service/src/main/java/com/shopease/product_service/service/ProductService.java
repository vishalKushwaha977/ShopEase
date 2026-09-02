package com.shopease.product_service.service;

import com.shopease.product_service.dto.ProductRequestDTO;
import com.shopease.product_service.dto.ProductResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO request);

    List<ProductResponseDTO> getAllProducts();

    Optional<ProductResponseDTO> getProductById(Long id);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO request);

    void deleteProduct(Long id);
}
