package com.reaksa.demo.mapper;

import com.reaksa.demo.dto.ProductDto;
import com.reaksa.demo.dto.ProductResponseDto;
import com.reaksa.demo.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product toEntity(ProductDto dto) {
        Product entity = new Product();

        entity.setProductName(dto.getProductName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    public void updateEntityFromDto(Product entity,ProductDto dto) {

        if (entity == null || dto == null) {
            return;
        }
        entity.setProductName(dto.getProductName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());

    }

    public ProductResponseDto toDto(Product entity) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(entity.getId());
        dto.setProductName(entity.getProductName());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public List<ProductResponseDto> toDtoList(List<Product> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(product -> this.toDto(product))
                .collect(Collectors.toList());
    }
}
