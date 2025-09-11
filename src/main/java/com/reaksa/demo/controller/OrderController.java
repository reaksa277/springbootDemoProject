package com.reaksa.demo.controller;

import com.reaksa.demo.dto.Order.OrderDto;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> listOrders() {
        return orderService.listOrders();
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> createOrder(@RequestBody OrderDto payload) {
        return orderService.createOrder(payload);
    }
}
