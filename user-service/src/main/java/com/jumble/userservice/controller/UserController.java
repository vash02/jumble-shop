package com.jumble.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jumble.userservice.model.AppUser;
import com.jumble.userservice.model.Order;
import com.jumble.userservice.model.OrderRequest;
import com.jumble.userservice.service.OrderClientService;
import com.jumble.userservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@SecurityRequirement(name = "Authorization")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderClientService orderClientService;
    @Autowired
    public HttpSession session;

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

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest order) {
        String token = (String) session.getAttribute("JWT_TOKEN");

        Order createdOrder = null;
        try {
            createdOrder = orderClientService.placeOrder(order, token);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Order-Status", "Order Placed Successfully");

        // Return ResponseEntity with complete response including headers, status code, and body
        return new ResponseEntity<>(createdOrder, headers, HttpStatus.OK);
    }
}
