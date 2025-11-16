package com.example.userlogin.controller;

import com.example.userlogin.Models.LoginRequest;
import com.example.userlogin.Models.LoginResponse;
import com.example.userlogin.Models.userinfo;
import com.example.userlogin.service.UserService;
import com.example.userlogin.service.jwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class Usercontroller {

    @Autowired
    private UserService service;

    @Autowired
    private jwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody userinfo user) {
        Optional<userinfo> existingUser = service.getUserByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            // Update existing user
            userinfo userToUpdate = existingUser.get();
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            userToUpdate.setRole(user.getRole());
            userToUpdate.setBusinessName(user.getBusinessName());
            userToUpdate.setStoreDescription(user.getStoreDescription());
            service.saveUser(userToUpdate);
            return ResponseEntity.ok("User updated successfully!");
        } else {
            // Save new user
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            service.saveUser(user);
            return ResponseEntity.ok("User registered successfully!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Authenticate using email + password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Fetch user from DB
            Optional<userinfo> userOptional = service.getUserByEmail(request.getEmail());
            if (userOptional.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            userinfo user = userOptional.get();

            // Generate token using email + role
            String token = jwtService.generateToken(user.getEmail(), user.getRole());

            // Return token + role
            return ResponseEntity.ok(new LoginResponse(token, user.getRole()));

        } catch (Exception e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
