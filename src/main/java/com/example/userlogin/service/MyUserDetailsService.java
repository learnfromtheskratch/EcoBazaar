package com.example.userlogin.service;

import com.example.userlogin.Models.UserPrincipal;
import com.example.userlogin.Models.userinfo;
import com.example.userlogin.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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

        userinfo user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserPrincipal(user); // Assuming UserPrincipal implements UserDetails
    }


}
