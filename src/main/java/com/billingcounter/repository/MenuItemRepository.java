package com.billingcounter.repository;

import com.billingcounter.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository: MenuItemRepository
 * Provides CRUD and query methods for MenuItem.
 *
 * Interview Note:
 * - Extends JpaRepository<MenuItem, Long>
 * - No need to implement — Spring Data auto-generates methods
 */

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    // Custom method to find all available items
    List<MenuItem> findByAvailableTrue();

    // Optional: find by category
    List<MenuItem> findByCategory(String category);
}
