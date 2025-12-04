package com.example.userlogin.service;

import com.example.userlogin.Models.UserPrincipal;
import com.example.userlogin.Models.userinfo;
import com.example.userlogin.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Trying to load user with email: " + email);
        userinfo user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("User found: " + user.getEmail() + ", encoded password: " + user.getPassword());
        return new UserPrincipal(user); // Assuming UserPrincipal implements UserDetails
    }
}

