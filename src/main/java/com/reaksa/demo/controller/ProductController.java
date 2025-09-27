package com.reaksa.demo.controller;

import com.reaksa.demo.dto.Product.ProductResponseDto;
import com.reaksa.demo.exception.model.DuplicateResourceException;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.dto.Product.ProductDto;
import com.reaksa.demo.exception.model.ResourceNotFoundException;
import com.reaksa.demo.repository.ProductRepository;
import com.reaksa.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;


    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> listProduct() {
        List<ProductResponseDto> products = productService.listProducts();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully list products", products));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponseWithDataModel> getProduct(@PathVariable("productId") long productId) {
        ProductResponseDto product = productService.getProduct(productId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully retrieve product", product));
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> createProduct(@Valid @RequestBody ProductDto payload) {
        productService.createProduct(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","successfully created product"));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<BaseResponseModel> updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto payload) {
        productService.updateProduct(productId, payload);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully updated product"));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<BaseResponseModel> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully deleted product"));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponseWithDataModel> searchProductsByFilter(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice

    ) {
        List<ProductResponseDto> products = productService.searchProduct(name, minPrice, maxPrice);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully search products", products));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponseModel>
     handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponseModel("fail", ex.getMessage()));
    }

}
