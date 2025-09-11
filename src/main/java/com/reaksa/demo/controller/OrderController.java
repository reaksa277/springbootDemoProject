package com.reaksa.demo.controller;

import com.reaksa.demo.dto.Order.OrderDto;
import com.reaksa.demo.dto.Order.UpdateOrderDto;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.service.OrderService;
import jakarta.validation.Valid;
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
    public ResponseEntity<BaseResponseModel> placeOrder(@RequestBody OrderDto payload) {
        return orderService.createOrder(payload);
    }

    @PatchMapping("{order_id}")
    public ResponseEntity<BaseResponseModel> updateOrderStatus(@PathVariable("order_id") Long orderId, @Valid @RequestBody UpdateOrderDto payload) {
        return orderService.updateOrderStatus(orderId, payload);
    }

    @DeleteMapping("{order_id}")
    public ResponseEntity<BaseResponseModel> deleteOrder(@PathVariable("order_id") Long orderId) {
        return orderService.deleteOrder(orderId);
    }
}
