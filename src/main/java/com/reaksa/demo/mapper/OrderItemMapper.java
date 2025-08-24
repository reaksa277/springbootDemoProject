package com.reaksa.demo.mapper;

import com.reaksa.demo.dto.Order.OrderItemDto;
import com.reaksa.demo.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItem toEntity(OrderItemDto dto) {
        OrderItem entity = new OrderItem();

        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getAmount());

        return entity;
    }
}
