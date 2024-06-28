package com.jumble.productservice.service;

import com.jumble.productservice.model.Product;
import com.jumble.productservice.model.ProductAvailabilityRequest;
import com.jumble.productservice.model.ProductAvailabilityResponse;
import com.jumble.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductAvailabilityService {

    @Autowired
    private ProductRepository productRepository;

    public ProductAvailabilityResponse checkAvailability(ProductAvailabilityRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        boolean isAvailable = product.getStockQuantity() >= request.getQuantity();
        return new ProductAvailabilityResponse(isAvailable, product.getStockQuantity());
    }
}

