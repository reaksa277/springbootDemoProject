package com.reaksa.demo.controller;

import com.reaksa.demo.dto.Product.ProductResponseDto;
import com.reaksa.demo.dto.base.PaginatedResponse;
import com.reaksa.demo.dto.base.Response;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.dto.Product.ProductDto;
import com.reaksa.demo.exception.model.ResourceNotFoundException;
import com.reaksa.demo.repository.ProductRepository;
import com.reaksa.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/paginated")
    public ResponseEntity<Response> listProductWithPagination(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PaginatedResponse<ProductResponseDto> products = productService.listProductWithPagination(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "successfully retrieved products with pagination", products));
    }

    @GetMapping
    public ResponseEntity<Response> listProduct() {
        List<ProductResponseDto> products = productService.listProducts();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "successfully listed products", products));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Response> getProduct(@PathVariable("productId") long productId) {
        ProductResponseDto product = productService.getProduct(productId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "successfully retrieved product", product));
    }

    @PostMapping
    public ResponseEntity<Response> createProduct(@Valid @RequestBody ProductDto payload) {
        productService.createProduct(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success("201", "success","successfully created product"));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Response> updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto payload) {
        productService.updateProduct(productId, payload);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success","successfully updated product"));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Response> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200" ,"success","successfully deleted product id: "+productId));
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchProductsByFilter(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice

    ) {
        List<ProductResponseDto> products = productService.searchProduct(name, minPrice, maxPrice);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "successfully retrieved products with filters", products));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponseModel>
     handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponseModel("fail", ex.getMessage()));
    }

}
