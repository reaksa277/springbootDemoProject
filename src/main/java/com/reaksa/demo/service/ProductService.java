package com.reaksa.demo.service;

import com.reaksa.demo.entity.Product;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.model.ProductModel;
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

    public ResponseEntity<BaseResponseWithDataModel> listProducts() {
        List<Product> products = productRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully list products", products));
    }

    public ResponseEntity<BaseResponseWithDataModel> getProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if(product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseWithDataModel("fail", "product not found with id : " + productId, null));
        }


        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully retrieve product ", product.get()));
    }

    public ResponseEntity<BaseResponseModel> createProduct(ProductModel product) {
        Product productEntity = new Product();

        productEntity.setProductName(product.getProductName());
        productEntity.setPrice(product.getPrice());
        productEntity.setDescription(product.getDescription());
        productEntity.setCreatedAt(LocalDateTime.now());

        productRepository.save(productEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success", "successfully created product"));
    }

    public ResponseEntity<BaseResponseModel> updateProduct(Long productId, ProductModel product) {
        Optional<Product> existing = productRepository.findById(productId);

        if(existing.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail", " product not found id : " + productId));
        }

        Product updatedProduct = existing.get();
        updatedProduct.setProductName(product.getProductName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setUpdatedAt(LocalDateTime.now());

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
