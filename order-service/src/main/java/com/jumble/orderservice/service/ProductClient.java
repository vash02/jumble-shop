package com.jumble.orderservice.service;

import com.jumble.orderservice.model.ProductAvailabilityRequest;
import com.jumble.orderservice.model.ProductAvailabilityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
        String url = productsServiceUrl + "/api/products/availability";

        ProductAvailabilityRequest request = new ProductAvailabilityRequest(productId, quantity);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<ProductAvailabilityRequest> entity = new HttpEntity<>(request);

        ResponseEntity<ProductAvailabilityResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, ProductAvailabilityResponse.class);

        return response.getBody();
    }
}
