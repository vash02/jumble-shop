package com.jumble.userservice.controller;

import com.jumble.userservice.model.AppUser;
import com.jumble.userservice.model.Order;
import com.jumble.userservice.model.OrderRequest;
import com.jumble.userservice.service.OrderClientService;
import com.jumble.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderClientService orderClientService;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials(); // Assuming the token is stored as credentials

        Order createdOrder = orderClientService.placeOrder(order, token);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Order-Status", "Order Placed Successfully");

        // Return ResponseEntity with complete response including headers, status code, and body
        return new ResponseEntity<>(createdOrder, headers, HttpStatus.OK);
    }
}
