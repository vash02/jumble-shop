package com.jumble.userservice.controller;

import com.jumble.userservice.model.AppUser;
import com.jumble.userservice.model.OrderRequest;
import com.jumble.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Long id) {
        AppUser user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<AppUser> getUserByUsername(@PathVariable String username) {
        AppUser user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AppUser> getUserByEmail(@PathVariable String email) {
        AppUser user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/orders/place")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest, @RequestHeader("Authorization") String token) {
        // Validate JWT token and authorize user
        // Example: Validate JWT token and authorize user

        // Forward request to Orders Service to place order
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<OrderRequest> requestEntity = new HttpEntity<>(orderRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://orders-service/api/orders/place",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return response;
    }
}
