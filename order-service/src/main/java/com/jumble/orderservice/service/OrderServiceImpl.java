package com.jumble.orderservice.service;

import com.jumble.orderservice.model.Order;
import com.jumble.orderservice.model.OrderRequest;
import com.jumble.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jumble.orderservice.utils.JwtUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductClient productClient;

    @Override
    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
        return order;
    }

    @Transactional
    public Order createOrderWithLock(Order order) {
        // Perform authentication and retrieve userId
        Long userId = order.getUserId();

        // Create new order
        Order newOrder = new Order();
        newOrder.setProductId(order.getProductId());
        newOrder.setQuantity(order.getQuantity());
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatus("PENDING");
        newOrder.setUserId(userId);

        // Update product quantity
        productClient.updateProductQuantity(order.getProductId(), order.getQuantity());

        // Save and return the order
        return orderRepository.save(newOrder);
    }


}
