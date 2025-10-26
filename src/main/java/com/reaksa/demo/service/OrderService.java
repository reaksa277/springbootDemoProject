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
import com.reaksa.demo.service.mail.NotificationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockManagementService stockManagementService;

    @Autowired
    private ApplicationConfiguration appConfig;

    @Autowired
    private NotificationService  notificationService;

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
        String threadName = Thread.currentThread().getName();

        log.info("[SYNC-ORDER] Creating new Order | Thread: {}", threadName);
        // reserve stock for order
        stockManagementService.reserveStockForOrder(payload.getOrderItems());

        // create order entity
        Order order = mapper.toEntity(payload);
        orderRepository.save(order);

        log.info("[SYNC-ORDER] Order created successfully with Order: {} | Thread: {}", order.getId(), threadName);
        log.info("[ASYNC-ORDER] Trigger and notification asynchronously for Order: {} | Thread: {}", order.getId(), threadName);

        notificationService.sendOrderConfirmationNotification(order.getId(), "Your order has been completed");

        log.info("[ASYNC-ORDER-COMPLETED] Completed order and triggered send notification.");
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
