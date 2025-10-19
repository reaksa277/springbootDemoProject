package com.reaksa.demo.service;

import com.reaksa.demo.common.config.ApplicationConfiguration;
import com.reaksa.demo.dto.Product.ProductResponseDto;
import com.reaksa.demo.dto.base.PaginatedResponse;
import com.reaksa.demo.entity.Product;
import com.reaksa.demo.exception.model.ResourceNotFoundException;
import com.reaksa.demo.mapper.ProductMapper;
import com.reaksa.demo.dto.Product.ProductDto;
import com.reaksa.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private ApplicationConfiguration appConfig;

    @Cacheable(value = "products-paginated", key = "T(com.reaksa.demo.common.util.CacheKeyGenerator).generatePaginatedKey('products', #pageable)")
    public PaginatedResponse listProductWithPagination(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        Page<ProductResponseDto> productPageDto = productPage.map(product -> mapper.toDto(product));

        return PaginatedResponse.from(productPageDto, appConfig.getPagination().getUrlByResourse("product"));
    }

    @Cacheable(value = "products", key = "'all'")
    public List<ProductResponseDto> listProducts() {
        List<Product> products = productRepository.findAll();

        return mapper.toDtoList(products);
    }

    public ProductResponseDto getProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id : " + productId));

        return mapper.toDto(product);
    }

    @CachePut(value = "products", key = "#product.getProductName()")
    public void createProduct(ProductDto product) {
        // validate if product is already exist
        if (productRepository.existsByProductName(product.getProductName())) {
            throw new ResourceNotFoundException("product is already existed");
        }

        Product productEntity = mapper.toEntity(product);

        productRepository.save(productEntity);
    }

    @CacheEvict(value = "products", key = "#productId")
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
