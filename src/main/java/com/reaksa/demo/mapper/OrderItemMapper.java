package com.reaksa.demo.mapper;

import com.reaksa.demo.dto.Order.OrderItemDto;
import com.reaksa.demo.dto.Order.OrderItemResponseDto;
import com.reaksa.demo.entity.OrderItem;
import com.reaksa.demo.entity.Product;
import com.reaksa.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemMapper {
    @Autowired
    private ProductRepository productRepository;

    public OrderItem toEntity(OrderItemDto dto) {
        OrderItem entity = new OrderItem();

        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getAmount());

        return entity;
    }

    public OrderItemResponseDto toResponseDto(OrderItem entity) {
        if(entity == null) {
            return null;
        }

        OrderItemResponseDto dto = new OrderItemResponseDto();

        dto.setProductId(entity.getProductId());
        dto.setPurchaseAmount(entity.getQuantity());

        // query get product
        Product product = productRepository.findById(entity.getProductId())
                .orElseThrow(() -> new RuntimeException("product not found with id: " + entity.getProductId()));

        dto.setProductName(product.getProductName());
        dto.setUnitPrice(product.getPrice());

        return dto;
    }

    public List<OrderItemResponseDto> toResponseDtoList(List<OrderItem> entityList) {
        return entityList
                .stream()
                .map(orderItem -> this.toResponseDto(orderItem))
                .collect(Collectors.toList());
    }
}
