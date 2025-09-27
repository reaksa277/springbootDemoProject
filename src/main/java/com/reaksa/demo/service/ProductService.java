package com.reaksa.demo.service;

import com.reaksa.demo.dto.Product.ProductResponseDto;
import com.reaksa.demo.dto.base.Response;
import com.reaksa.demo.entity.Product;
import com.reaksa.demo.exception.model.ResourceNotFoundException;
import com.reaksa.demo.mapper.ProductMapper;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.dto.Product.ProductDto;
import com.reaksa.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    public List<ProductResponseDto> listProducts() {
        List<Product> products = productRepository.findAll();

        return mapper.toDtoList(products);
    }

    public ProductResponseDto getProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id : " + productId));

        return mapper.toDto(product);
    }

    public void createProduct(ProductDto product) {
        // validate if product is already exist
        if (productRepository.existsByProductName(product.getProductName())) {
            throw new ResourceNotFoundException("product is already existed");
        }

        Product productEntity = mapper.toEntity(product);

        productRepository.save(productEntity);
    }

    public void updateProduct(Long productId, ProductDto product) {

        Product existing = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("product not found with id : " + productId));

        mapper.updateEntityFromDto(existing, product);

        productRepository.save(existing);
    }

    public void deleteProduct(Long productId) {
        if(!productRepository.existsById(productId)){
            throw new ResourceNotFoundException("product not found with id : " + productId);
        }

        productRepository.deleteById(productId);
    }

    public List<ProductResponseDto> searchProduct(String name, Double minPrice, Double maxPrice) {
        String formatedName = name != null ? name.toLowerCase() : null;

        List<Product> products = productRepository.findProductsWithFilters(formatedName, minPrice, maxPrice);

        return mapper.toDtoList(products);
    }
}
