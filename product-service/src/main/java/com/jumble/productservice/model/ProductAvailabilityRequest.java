package com.jumble.productservice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductAvailabilityRequest {
    private Long productId;
    private int quantity;

    // Constructors, getters, and setters
    public ProductAvailabilityRequest() {
    }

    public ProductAvailabilityRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

}