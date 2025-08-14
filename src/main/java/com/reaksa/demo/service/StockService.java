package com.reaksa.demo.service;

import com.reaksa.demo.dto.Stock.StockResponseDto;
import com.reaksa.demo.entity.Product;
import com.reaksa.demo.entity.Stock;
import com.reaksa.demo.exception.model.ResourceNotFoundException;
import com.reaksa.demo.mapper.StockMapper;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.dto.Stock.StockDto;
import com.reaksa.demo.dto.Stock.UpdateStockDto;
import com.reaksa.demo.repository.ProductRepository;
import com.reaksa.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockMapper mapper;

    public ResponseEntity<BaseResponseWithDataModel> listStock() {
        List<Stock> stocks = stockRepository.findAll();

        List<StockResponseDto> dtos = mapper.toListDto(stocks);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully list stock!", dtos));
    }

    public ResponseEntity<BaseResponseWithDataModel> getStock(Long stockId) {

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("stock not found with id : " + stockId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully retrieve stock!", stock));
    }

    public ResponseEntity<BaseResponseModel> createStock(StockDto stock) {
        Product existingProduct = productRepository.findById(stock.getProductId())
                .orElseThrow(() ->  new ResourceNotFoundException("product not found with id : " + stock.getProductId()));

        Stock  stockEntity = mapper.toEntity(stock,  existingProduct);

        stockRepository.save(stockEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success", "successfully created stock"));
    }

    // update stock
    /*
    public ResponseEntity<BaseResponseModel> updateStock(Long stockId, StockDto stock) {
        Optional<Stock> stockEntity = stockRepository.findById(stockId);

        if(stockEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail", "stock not found id : " + stockId));
        }

        Stock updatedStock = stockEntity.get();
        mapper.updateEntityFromDto(updatedStock, stock);

        stockRepository.save(updatedStock);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success", "successfully updated stock id : " + stockId));
    }
    */

    public ResponseEntity<BaseResponseModel> adjustQuantity(Long stockId, UpdateStockDto updateStock) {

        Stock exsitingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("stock not found with id : " + stockId));

        if(updateStock.getOperationType() == 1) {
            int newQty = exsitingStock.getQuantity() + updateStock.getQuantity();

            exsitingStock.setQuantity(newQty);
        } else if(updateStock.getOperationType() == 2) {
            if (exsitingStock.getQuantity() < updateStock.getQuantity()) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(new BaseResponseModel("fail", "quantity to remove cannot be exceeded than existing stock"));
            }

            int newQty = exsitingStock.getQuantity()  - updateStock.getQuantity();

            exsitingStock.setQuantity(newQty);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponseModel("fail", "operation type not supported"));
        }

        stockRepository.save(exsitingStock);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success", "successfully updated stock"));
    }

    public ResponseEntity<BaseResponseModel> deleteStock(Long stockId) {
        Optional<Stock> stockEntity = stockRepository.findById(stockId);

        if(stockEntity.isEmpty()) {
            throw new ResourceNotFoundException("stock not found with id : " + stockId);
        }

        stockRepository.deleteById(stockId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new BaseResponseModel("success", "successfully deleted stock id : " + stockId));
    }

}
