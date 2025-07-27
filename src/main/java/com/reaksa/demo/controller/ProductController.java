package com.reaksa.demo.controller;

import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.dto.Product.ProductDto;
import com.reaksa.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> listProduct() {
        return productService.listProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponseWithDataModel> getProduct(@PathVariable("productId") long productId) {
        return productService.getProduct(productId);
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> createProduct(@RequestBody ProductDto payload) {
        return productService.createProduct(payload);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<BaseResponseModel> updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto payload) {
        return productService.updateProduct(productId, payload);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<BaseResponseModel> deleteProduct(@PathVariable("productId") Long productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponseWithDataModel> searchProductsByFilter(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice

    ) {
        return productService.searchProduct(name, minPrice, maxPrice);
    }

}
