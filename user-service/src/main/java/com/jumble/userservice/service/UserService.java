package com.jumble.userservice.service;

import com.jumble.userservice.model.AppUser;
import com.jumble.userservice.model.Order;
import com.jumble.userservice.repository.UserRepository;
import com.jumble.userservice.utils.JwtUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    public AppUser registerUser(AppUser user) {
        // Check if username already exists
        Optional<AppUser> existingUserByUsername = userRepository.findByUsername(user.getUsername());
        if (existingUserByUsername.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email already exists
        Optional<AppUser> existingUserByEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserByEmail.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public String loginUser(AppUser user) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            AppUser authenticatedUser = (AppUser) authentication.getPrincipal();
            return jwtUtil.generateToken(authenticatedUser);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid Credentials"+ e);
        }
    }

    public AppUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));
    }

    public Long getUserIdByUsername(String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return user.getId();
    }

    public AppUser getUserByUsername(String username) {
        Optional<AppUser> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("User not found with username: " + username);
        }
    }



    public void deleteUser(Long id) {
        AppUser user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    public AppUser updateUser(Long id, AppUser userDetails) {
        AppUser user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        // Only update the password if a new password is provided
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        return userRepository.save(user);
    }

    public AppUser getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with username"));
    }
}
