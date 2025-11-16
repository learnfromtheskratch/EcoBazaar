package com.example.userlogin.Models;

public class LoginResponse {
    private String token;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String role;

    public LoginResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

}
