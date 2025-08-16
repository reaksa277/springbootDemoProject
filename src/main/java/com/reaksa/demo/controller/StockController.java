package com.reaksa.demo.controller;

import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.dto.Stock.StockDto;
import com.reaksa.demo.dto.Stock.UpdateStockDto;
import com.reaksa.demo.service.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> listStock() {
        return stockService.listStock();
    }

    @GetMapping("/{stockID}")
    public ResponseEntity<BaseResponseWithDataModel> getStock(@PathVariable Long stockID) {
        return stockService.getStock(stockID);
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> addStock(@Valid @RequestBody StockDto payload) {
        return stockService.createStock(payload);
    }

//    @PutMapping("/{stockId}")
//    public ResponseEntity<BaseResponseModel> updateStock(@PathVariable("stockId") Long stockId, @RequestBody StockDto payload) {
//        return stockService.updateStock(stockId, payload);
//    }

    @PatchMapping("/{stockId}")
    public ResponseEntity<BaseResponseModel> adjustQuantity(@PathVariable("stockId") Long stockId, @Valid @RequestBody
    UpdateStockDto payload) {
        return stockService.adjustQuantity(stockId, payload);
    }

    @DeleteMapping("/{stockId}")
    public ResponseEntity<BaseResponseModel> deleteStock(@PathVariable("stockId") Long stockId) {
        return stockService.deleteStock(stockId);
    }
}
