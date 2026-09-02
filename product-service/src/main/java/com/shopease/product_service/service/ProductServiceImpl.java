package com.shopease.product_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shopease.product_service.dto.ProductRequestDTO;
import com.shopease.product_service.dto.ProductResponseDTO;
import com.shopease.product_service.entity.Product;
import com.shopease.product_service.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        Product product1 = new Product();
        product1.setName(request.getName());
        if (productRepository.findBySku(request.getSku()).isPresent()) {
           throw new RuntimeException("SKU already exists: " + request.getSku());
        }
        product1.setSku(request.getSku());
        product1.setDescription(request.getDescription());
        product1.setPrice(request.getPrice());
        product1.setCategory(request.getCategory());
        product1.setStockQuantity(request.getStockQuantity());
        Product savedProduct = productRepository.save(product1);
        return convertToResponse(savedProduct);
    }


    @Override
    public List<ProductResponseDTO> getAllProducts() {
       return productRepository.findAll()
                               .stream()
                               .map(this::convertToResponse)
                               .toList();                 
    }

    @Override
    public Optional<ProductResponseDTO> getProductById(Long id) {
       return productRepository.findById(id)
                               .map(this::convertToResponse);
    }

   @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + id));

        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setCategory(request.getCategory());
        existingProduct.setStockQuantity(request.getStockQuantity());

        Product updatedProduct = productRepository.save(existingProduct);

        return convertToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


    private ProductResponseDTO convertToResponse(Product product) {
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(product.getId());
        response.setSku(product.getSku());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setCategory(product.getCategory());
        response.setStockQuantity(product.getStockQuantity());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }
}
