package com.reaksa.demo.service;

import com.reaksa.demo.common.config.ApplicationConfiguration;
import com.reaksa.demo.dto.Stock.StockResponseDto;
import com.reaksa.demo.dto.base.PaginatedResponse;
import com.reaksa.demo.entity.Product;
import com.reaksa.demo.entity.Stock;
import com.reaksa.demo.exception.model.ResourceNotFoundException;
import com.reaksa.demo.exception.model.UnprocessableEntityException;
import com.reaksa.demo.mapper.StockMapper;
import com.reaksa.demo.dto.Stock.StockDto;
import com.reaksa.demo.dto.Stock.UpdateStockDto;
import com.reaksa.demo.repository.ProductRepository;
import com.reaksa.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ApplicationConfiguration appConfig;

    public PaginatedResponse listStocksWithPagination(Pageable  pageable) {
        Page<Stock> stockPages = stockRepository.findAll(pageable);
        Page<StockResponseDto> stockPageDto = stockPages.map(stock -> mapper.toDto(stock));

        return PaginatedResponse.from(stockPageDto, appConfig.getPagination().getUrlByResourse("stock"));
    }

    public List<StockResponseDto> listStock() {
        List<Stock> stocks = stockRepository.findAll();

        return mapper.toListDto(stocks);
    }

    public StockResponseDto getStock(Long stockId) {

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("stock not found with id : " + stockId));

        return mapper.toDto(stock);
    }

    public void createStock(StockDto stock) {
        Product existingProduct = productRepository.findById(stock.getProductId())
                .orElseThrow(() ->  new ResourceNotFoundException("product not found with id : " + stock.getProductId()));

        Stock  stockEntity = mapper.toEntity(stock,  existingProduct);

        stockRepository.save(stockEntity);
    }

    public void adjustQuantity(Long stockId, UpdateStockDto updateStock) {

        Stock exsitingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("stock not found with id : " + stockId));

        if(updateStock.getOperationType() == 1) {
            int newQty = exsitingStock.getQuantity() + updateStock.getQuantity();

            exsitingStock.setQuantity(newQty);
        } else if(updateStock.getOperationType() == 2) {
            if (exsitingStock.getQuantity() < updateStock.getQuantity()) {
                throw new UnprocessableEntityException("quantity to remove cannot be exceeded than existing stock" + exsitingStock.getQuantity());
            }

            int newQty = exsitingStock.getQuantity()  - updateStock.getQuantity();

            exsitingStock.setQuantity(newQty);
        } else {
            throw new IllegalArgumentException("operation type not supported");
        }

        stockRepository.save(exsitingStock);
    }

    public void deleteStock(Long stockId) {
        Optional<Stock> stockEntity = stockRepository.findById(stockId);

        if(stockEntity.isEmpty()) {
            throw new ResourceNotFoundException("stock not found with id : " + stockId);
        }

        stockRepository.deleteById(stockId);
    }

}
