package com.jumble.productservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductAvailabilityResponse {
    private boolean available;
    private int availableQuantity;

    // Constructors, getters, and setters
    public ProductAvailabilityResponse() {
    }

    public ProductAvailabilityResponse(boolean available, int availableQuantity) {
        this.available = available;
        this.availableQuantity = availableQuantity;
    }

}
