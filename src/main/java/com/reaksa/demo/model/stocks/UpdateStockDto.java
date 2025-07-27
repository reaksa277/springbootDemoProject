package com.reaksa.demo.model.stocks;

import lombok.Data;

@Data
public class UpdateStockDto {
    private Integer operationType; // 1 -> add, 2 -> remove
    private Integer quantity;
}
