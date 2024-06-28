package com.jumble.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumble.userservice.model.Order;
import com.jumble.userservice.model.OrderRequest;
import com.jumble.userservice.repository.UserRepository;
import com.jumble.userservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderClientService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${orders.service.url}")
    private String ordersServiceUrl;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Order placeOrder(OrderRequest orderRequest, String token) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", orderRequest.getProductId());
        requestBody.put("quantity", orderRequest.getQuantity());
        requestBody.put("orderDate", null); // Example: Set your orderDate appropriately
        requestBody.put("status", null); // Example: Set your status appropriately
        requestBody.put("userId", userService.getUserIdByUsername(jwtUtil.extractUsername(token)));

        // Convert map to JSON string
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBodyJson, headers);

        ResponseEntity<Order> responseEntity = restTemplate.exchange(
                ordersServiceUrl + "/api/orders/create",
                HttpMethod.POST,
                requestEntity,
                Order.class
        );

        return responseEntity.getBody();
    }
}

