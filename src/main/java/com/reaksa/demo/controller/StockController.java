package com.reaksa.demo.controller;

import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.model.stocks.StockModel;
import com.reaksa.demo.model.stocks.UpdateStockModel;
import com.reaksa.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> getStock() {
        return stockService.listStock();
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> addStock(@RequestBody StockModel payload) {
        return stockService.createStock(payload);
    }

    @PutMapping("/{stockId}")
    public ResponseEntity<BaseResponseModel> updateStock(@PathVariable("stockId") Long stockId, @RequestBody StockModel payload) {
        return stockService.updateStock(stockId, payload);
    }

    @PatchMapping("/{stockId}")
    public ResponseEntity<BaseResponseModel> adjustQuantity(@PathVariable("stockId") Long stockId, @RequestBody
    UpdateStockModel payload) {
        return stockService.adjustQuantity(stockId, payload);
    }

    @DeleteMapping("/{stockId}")
    public ResponseEntity<BaseResponseModel> deleteStock(@PathVariable("stockId") Long stockId) {
        return stockService.deleteStock(stockId);
    }
}
