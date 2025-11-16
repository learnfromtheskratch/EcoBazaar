package com.example.userlogin.repo;

import com.example.userlogin.Models.userinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<userinfo, String> {
    Optional<userinfo> findByEmail(String email);
}

