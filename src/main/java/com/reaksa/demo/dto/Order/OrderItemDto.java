package com.reaksa.demo.dto.Order;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemDto {
    @JsonProperty("product_id")
    @NotNull(message = "product id is required")
    private Long productId;

    @NotNull(message = "amount is required")
    @Min(value = 1, message = "amount must be at least 1!")
    private Integer amount;
}
