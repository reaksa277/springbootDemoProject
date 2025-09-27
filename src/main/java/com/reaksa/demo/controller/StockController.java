package com.reaksa.demo.controller;

import com.reaksa.demo.dto.Stock.StockResponseDto;
import com.reaksa.demo.dto.base.Response;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.dto.Stock.StockDto;
import com.reaksa.demo.dto.Stock.UpdateStockDto;
import com.reaksa.demo.service.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<Response> listStock() {
        List<StockResponseDto> stocks = stockService.listStock();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "successfully retrieved stocks", stocks));
    }

    @GetMapping("/{stockID}")
    public ResponseEntity<Response> getStock(@PathVariable Long stockID) {
        StockResponseDto stock = stockService.getStock(stockID);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "stock found", stock));
    }

    @PostMapping
    public ResponseEntity<Response> addStock(@Valid @RequestBody StockDto payload) {
        stockService.createStock(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success("201", "success", "stock created"));
    }

    @PatchMapping("/{stockId}")
    public ResponseEntity<Response> adjustQuantity(@PathVariable("stockId") Long stockId, @Valid @RequestBody
    UpdateStockDto payload) {
        stockService.adjustQuantity(stockId, payload);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "stock adjusted"));
    }

    @DeleteMapping("/{stockId}")
    public ResponseEntity<Response> deleteStock(@PathVariable("stockId") Long stockId) {
        stockService.deleteStock(stockId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "stock deleted"));
    }
}
