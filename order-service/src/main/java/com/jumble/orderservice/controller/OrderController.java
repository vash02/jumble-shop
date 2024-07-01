package com.jumble.orderservice.controller;

import com.jumble.orderservice.model.Order;
import com.jumble.orderservice.model.OrderRequest;
import com.jumble.orderservice.model.ProductAvailabilityResponse;
import com.jumble.orderservice.service.OrderService;
import com.jumble.orderservice.service.ProductClient;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "Authorization")
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductClient productClient;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            ProductAvailabilityResponse availabilityResponse = productClient.checkProductAvailability(order.getProductId(), order.getQuantity());
            System.out.println("availability response "+availabilityResponse);
            if (!availabilityResponse.isAvailable()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not available in the requested quantity");
            }
        //Pessimistic Locking for creating order entries in DB
            Order createdOrder = orderService.createOrderWithLock(order);

            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("get/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }
}

