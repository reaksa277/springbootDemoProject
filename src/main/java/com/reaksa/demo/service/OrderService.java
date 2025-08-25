package com.reaksa.demo.service;

import com.reaksa.demo.dto.Order.OrderDto;
import com.reaksa.demo.entity.Order;
import com.reaksa.demo.mapper.OrderMapper;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderRepository orderRepository;

    public ResponseEntity<BaseResponseModel> createOrder(OrderDto payload) {
        Order order = mapper.toEntity(payload);

        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new BaseResponseModel("success", "successfully placed order"));
    }
}
