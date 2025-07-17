package com.reaksa.demo.controller;

import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.model.StockModel;
import com.reaksa.demo.service.ProductService;
import com.reaksa.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
}
