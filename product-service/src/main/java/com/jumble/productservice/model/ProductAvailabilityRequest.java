package com.jumble.productservice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductAvailabilityRequest {
    private Long productId;
    private int requestedQuantity;

    // Constructors, getters, and setters
    public ProductAvailabilityRequest() {
    }

    public ProductAvailabilityRequest(Long productId, int requestedQuantity) {
        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
    }

}