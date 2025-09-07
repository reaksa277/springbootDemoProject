package com.reaksa.demo.service;

import com.reaksa.demo.dto.Order.OrderDto;
import com.reaksa.demo.dto.Order.OrderItemDto;
import com.reaksa.demo.entity.Order;
import com.reaksa.demo.entity.Stock;
import com.reaksa.demo.mapper.OrderMapper;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.repository.OrderRepository;
import com.reaksa.demo.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class OrderService {
    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    @Transactional
    public ResponseEntity<BaseResponseModel> createOrder(OrderDto payload) {

        // map for product ids
        List<Long> productIds = payload.getOrderItems().stream()
                        .map(OrderItemDto::getProductId).toList();

        // get stocks in productIds
        List<Stock> stocks = stockRepository.findByProductIdIn(productIds,
                Sort.by(Sort.Direction.ASC, "createdAt"));

        // map for required quantity of productIds
        // example: 1: 100, 2: 50
        Map<Long, Integer> requiredQuantities = payload.getOrderItems().stream()
                        .collect(Collectors.toMap(OrderItemDto::getProductId, OrderItemDto::getAmount));
//
//        // deduct stock for each product
//        // [1,3]
        for(Long productId : requiredQuantities.keySet()) {
            // quantity to deduct
            int remain = requiredQuantities.get(productId);

            // filter stocks by product id
            List<Stock> stocksByProduct = stocks.stream()
                    .filter(stock -> stock.getProduct().getId().equals(productId))
                    .toList();

            // calculate and compare qty
            for(Stock stock : stocksByProduct) {
                if(remain <= 0) break;

                int available = stock.getQuantity();

                if(available >= remain) {
                    stock.setQuantity((available - remain));
                    remain = 0;
                } else {
                    stock.setQuantity(0);
                    remain -= available;
                }
            }

            if(remain > 0) {
                throw new RuntimeException("Not enough stocks for this product id: " + productId);
            }
        }

        // save update stocks to DB
        stockRepository.saveAll(stocks);

        // create order entity
        Order order = mapper.toEntity(payload);

        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new BaseResponseModel("success", "successfully placed order"));
    }
}
