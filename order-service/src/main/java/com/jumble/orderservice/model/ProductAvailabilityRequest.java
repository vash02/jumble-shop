package com.jumble.orderservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAvailabilityRequest {
    private Long productId;
    private int quantity;

    public ProductAvailabilityRequest() {
    }

    public ProductAvailabilityRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
