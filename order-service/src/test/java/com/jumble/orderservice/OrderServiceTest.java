package com.jumble.orderservice;

import com.jumble.orderservice.model.Order;
import com.jumble.orderservice.model.OrderRequest;
import com.jumble.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    @Transactional
    public void testConcurrentOrderCreation() throws InterruptedException {
        Long productId = 4L;
        Order orderRequest1 = new Order(productId, 5L, LocalDateTime.now(), "PENDING", 1L);
        Order orderRequest2 = new Order(productId, 5L, LocalDateTime.now(), "PENDING", 1L);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> orderService.createOrderWithLock(orderRequest1));
        executor.submit(() -> orderService.createOrderWithLock(orderRequest2));

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    @Transactional
    public void testConcurrentOrderUpdating() throws InterruptedException {
        Long orderId = 3L;
        String status1 = "PROCESSING";
        String status2 = "CANCELLED";
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> orderService.updateOrderStatus(orderId, status1));
        executor.submit(() -> orderService.updateOrderStatus(orderId, status2));

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}

