package com.jumble.orderservice.service;

import com.jumble.orderservice.model.ProductAvailabilityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

// ProductClient.java
@Component
public class ProductClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${products.service.url}")
    private String productsServiceUrl;

    public ProductAvailabilityResponse checkProductAvailability(Long productId, int quantity) {
        String url = productsServiceUrl + "/api/products/check-availability?productId=" + productId + "&quantity=" + quantity;
        ResponseEntity<ProductAvailabilityResponse> response = restTemplate.getForEntity(url, ProductAvailabilityResponse.class);
        return response.getBody();
    }
}
