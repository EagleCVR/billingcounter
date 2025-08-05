package com.billingcounter.dto;

public class SignupRequest {
    private String username;
    private String password;
    private String role; // "ADMIN", "WAITER", etc.

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
