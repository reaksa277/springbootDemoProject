package com.reaksa.demo.service;

import com.reaksa.demo.dto.ProductResponseDto;
import com.reaksa.demo.entity.Product;
import com.reaksa.demo.mapper.ProductMapper;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.dto.ProductDto;
import com.reaksa.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    public ResponseEntity<BaseResponseWithDataModel> listProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponseDto> dtos = mapper.toDtoList(products);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully list products", dtos));
    }

    public ResponseEntity<BaseResponseWithDataModel> getProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if(product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseWithDataModel("fail", "product not found with id : " + productId, null));
        }

        ProductResponseDto dto = mapper.toDto(product.get());


        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully retrieve product ", dto));
    }

    public ResponseEntity<BaseResponseModel> createProduct(ProductDto product) {
        Product productEntity = mapper.toEntity(product);

        productRepository.save(productEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success", "successfully created product"));
    }

    public ResponseEntity<BaseResponseModel> updateProduct(Long productId, ProductDto product) {
        Optional<Product> existing = productRepository.findById(productId);

        if(existing.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail", " product not found id : " + productId));
        }

        Product updatedProduct = existing.get();
        mapper.updateEntityFromDto(updatedProduct, product);

        productRepository.save(updatedProduct);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success", "successfully updated product id : " + productId));
    }

    public ResponseEntity<BaseResponseModel> deleteProduct(Long productId) {
        if(!productRepository.existsById(productId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail", " product not found id : " + productId));
        }

        productRepository.deleteById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new BaseResponseModel("success", "successfully deleted product id : " + productId));
    }

    public ResponseEntity<BaseResponseWithDataModel> searchProduct(String name, Double minPrice, Double maxPrice) {
        String formatedName = name != null ? name.toLowerCase() : null;

        List<Product> product = productRepository.findProductsWithFilters(formatedName, minPrice, maxPrice);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully search product with filters", product));
    }
}
