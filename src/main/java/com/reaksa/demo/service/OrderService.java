package com.reaksa.demo.service;

import com.reaksa.demo.common.config.ApplicationConfiguration;
import com.reaksa.demo.dto.Order.OrderDto;
import com.reaksa.demo.dto.Order.OrderResponseDto;
import com.reaksa.demo.dto.Order.UpdateOrderDto;
import com.reaksa.demo.dto.base.PaginatedResponse;
import com.reaksa.demo.entity.Order;
import com.reaksa.demo.exception.model.ResourceNotFoundException;
import com.reaksa.demo.mapper.OrderMapper;
import com.reaksa.demo.repository.OrderRepository;
import com.reaksa.demo.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {
    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockManagementService stockManagementService;

    @Autowired
    private ApplicationConfiguration appConfig;

    public PaginatedResponse listOrdersWithPagination(Pageable pageable){
        Page<Order> orderPages = orderRepository.findAll(pageable);
        Page<OrderResponseDto> orderPageDto = orderPages.map(order -> mapper.toResponseDto(order));

        return PaginatedResponse.from(orderPageDto, appConfig.getPagination().getUrlByResourse("order"));
    }

    public List<OrderResponseDto> listOrders() {
        List<Order> orders = orderRepository.findAll();

        return mapper.toResponseDtoList(orders);
    }

    @Transactional
    public void createOrder(OrderDto payload) {
        // reserve stock for order
        stockManagementService.reserveStockForOrder(payload.getOrderItems());
    }

    public OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderDto payload) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Order not found with id: " + orderId);
                });

        mapper.updateEntityFromDto(existingOrder, payload);
        orderRepository.save(existingOrder);

        return mapper.toResponseDto(existingOrder);
    }

    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }

        orderRepository.deleteById(orderId);
    }
}
