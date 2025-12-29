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
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
    private DaoAuthenticationProvider authenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody userinfo user) {
        Optional<userinfo> existingUser = service.getUserByEmail(user.getEmail());
        System.out.println(existingUser.isPresent());
        if (existingUser.isPresent()) {
            // Update existing user (only encode raw password)
            userinfo userToUpdate = existingUser.get();
            if (!passwordEncoder.matches(user.getPassword(), userToUpdate.getPassword())) {
                // Only re-encode if password changed
                userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userToUpdate.setRole(user.getRole());
            userToUpdate.setBusinessName(user.getBusinessName());
            userToUpdate.setStoreDescription(user.getStoreDescription());
            service.saveUser(userToUpdate);
            return ResponseEntity.ok("User updated successfully!");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            service.saveUser(user);

            return ResponseEntity.ok("User registered successfully!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            userinfo user = service.getUserByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtService.generateToken(user.getEmail(), user.getRole());

            return ResponseEntity.ok(new LoginResponse(token, user.getRole()));

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }


}



