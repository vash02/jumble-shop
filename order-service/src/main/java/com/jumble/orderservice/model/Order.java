package com.jumble.orderservice.model;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;
    private LocalDateTime orderDate;
    private String status;

    public Order(Long productId, Long id, LocalDateTime orderDate, String status, long userId) {
        this.productId = productId;
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.userId = userId;
    }

    public Order(){}
}

