package com.reaksa.demo.model.stocks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockModel {
    private Long productId;
    private Integer quantity;
}
