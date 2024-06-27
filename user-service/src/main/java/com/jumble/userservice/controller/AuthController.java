package com.jumble.userservice.controller;

import com.jumble.userservice.model.AppUser;
import com.jumble.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AppUser user) {
        try{
        AppUser registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AppUser user) {
        String token = userService.loginUser(user);
        return ResponseEntity.ok(token);
    }
}
