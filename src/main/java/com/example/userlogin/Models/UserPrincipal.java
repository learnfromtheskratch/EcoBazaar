package com.example.userlogin.Models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private userinfo user;

    public UserPrincipal(userinfo user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return role dynamically from DB
        return Collections.singleton(
                new SimpleGrantedAuthority("ROLE_" + user.getRole())
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Must override getUsername(), not getEmail()
    @Override
    public String getUsername() {
        return user.getEmail();   // login using email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
