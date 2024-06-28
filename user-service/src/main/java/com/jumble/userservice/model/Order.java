package com.jumble.userservice.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Order {
    @NotNull
    private Long id;
    @NotNull
    private Long productId;
    @NotNull
    private int quantity;
    private String status;
    @NonNull
    private Long userId;
    private LocalDateTime orderDate;

    // Constructors, getters, and setters
    public Order() {
    }

    public Order(Long id, Long productId, int quantity, String status, Long userId, LocalDateTime orderDate) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
        this.userId = userId;
        this.orderDate = orderDate;
    }
}
