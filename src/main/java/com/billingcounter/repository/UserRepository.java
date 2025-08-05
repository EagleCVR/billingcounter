package com.billingcounter.repository;

import com.billingcounter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username (used for login/authentication)
    Optional<User> findByUsername(String username);

    // Optional: Check if a user exists
    boolean existsByUsername(String username);
}
