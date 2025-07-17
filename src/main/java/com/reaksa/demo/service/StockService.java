package com.reaksa.demo.service;

import com.reaksa.demo.entity.Stock;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.model.StockModel;
import com.reaksa.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public ResponseEntity<BaseResponseWithDataModel> listStock() {
        List<Stock> stocks = stockRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully list stock!", stocks));
    }

    public ResponseEntity<BaseResponseModel> createStock(@RequestBody StockModel stock) {
        Stock  stockEntity = new Stock();

        stockEntity.setProductId(stock.getProductId());
        stockEntity.setQuantity(stock.getQuantity());
        stockEntity.setCreatedAt(LocalDateTime.now());

        stockRepository.save(stockEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success", "successfully created stock"));
    }

}
