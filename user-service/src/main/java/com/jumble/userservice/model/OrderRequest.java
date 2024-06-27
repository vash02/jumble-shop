package com.jumble.userservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRequest {
    private Long productId;
    private int quantity;

    // Constructors, getters, and setters
    public OrderRequest() {
    }

    public OrderRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

}
