package com.example.userlogin.service;

import com.example.userlogin.Models.userinfo;
import com.example.userlogin.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
    public Optional<userinfo> getUserByEmail(String email) {
        return Optional.ofNullable(repo.findByEmail(email).orElse(null));
    }

    @Transactional
    public ResponseEntity<String> saveUser(userinfo user) {
        try {
            // Check if email already exists
            if (repo.findByEmail(user.getEmail()).isPresent()) {
                return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
            }

            // Validate role
            if (!user.getRole().matches("USER|SELLER|ADMIN")) {
                return new ResponseEntity<>("Invalid role", HttpStatus.BAD_REQUEST);
            }

            if ("SELLER".equalsIgnoreCase(user.getRole())) {
                if (user.getBusinessName() == null || user.getBusinessName().isEmpty() ||
                        user.getStoreDescription() == null || user.getStoreDescription().isEmpty()) {
                    return new ResponseEntity<>("Seller must provide businessName and storeDescription",
                            HttpStatus.BAD_REQUEST);
                }
            }
            // Encrypt password
            user.setPassword(encoder.encode(user.getPassword()));

            // Save user
            repo.save(user);

            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

