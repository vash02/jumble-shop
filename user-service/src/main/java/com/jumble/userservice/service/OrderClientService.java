package com.jumble.userservice.service;

import com.jumble.userservice.model.Order;
import com.jumble.userservice.model.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderClientService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${orders.service.url}")
    private String ordersServiceUrl;

    public Order placeOrder(OrderRequest orderRequest, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrderRequest> requestEntity = new HttpEntity<>(orderRequest, headers);

        ResponseEntity<Order> responseEntity = restTemplate.exchange(
                ordersServiceUrl + "/api/orders/create",
                HttpMethod.POST,
                requestEntity,
                Order.class
        );

        return responseEntity.getBody();
    }
}

