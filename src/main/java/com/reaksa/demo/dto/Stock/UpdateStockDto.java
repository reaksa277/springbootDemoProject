package com.reaksa.demo.dto.Stock;

import lombok.Data;

@Data
public class UpdateStockDto {
    private Integer operationType; // 1 -> add, 2 -> remove
    private Integer quantity;
}
