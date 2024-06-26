package com.jumble.orderservice.service;

import com.jumble.orderservice.model.Order;
import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrderById(Long id);
    List<Order> getOrdersByUserId(Long userId);
    Order updateOrderStatus(Long id, String status);
}
