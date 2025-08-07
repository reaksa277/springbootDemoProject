package com.reaksa.demo.dto.Order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDto {
    @JsonProperty("order_id")
    private Long id;
    private String status;
    private Double total;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    private List<OrderItemResponseDto> items;
}
