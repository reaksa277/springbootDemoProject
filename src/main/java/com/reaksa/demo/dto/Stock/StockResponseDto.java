package com.reaksa.demo.dto.Stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"stock_id", "product_id", "quantity", "created_at", "updated_at"})
public class StockResponseDto {
    @JsonProperty("stock_id")
    private Long id;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("quantity")
    private Integer qty;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

}
