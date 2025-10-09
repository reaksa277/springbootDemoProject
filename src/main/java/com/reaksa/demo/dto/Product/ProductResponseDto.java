package com.reaksa.demo.dto.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"product_id", "product_name", "price", "description", "created_at", "updated_at"})
public class ProductResponseDto implements Serializable {
    @JsonProperty("product_id")
    private Long id;
    @JsonProperty("product_name")
    private String productName;
    private Double price;

    @JsonProperty("total_price")
    private Long totalPrice;

    private String description;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
