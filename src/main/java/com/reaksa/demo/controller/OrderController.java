package com.reaksa.demo.controller;

import com.reaksa.demo.dto.Order.OrderDto;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<BaseResponseModel> createOrder(@RequestBody OrderDto payload) {
        return orderService.createOrder(payload);
    }
}
