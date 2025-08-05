package com.billingcounter.repository;

import com.billingcounter.model.MenuItemGroceryUsage;
import com.billingcounter.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemGroceryUsageRepository extends JpaRepository<MenuItemGroceryUsage, Long> {

    List<MenuItemGroceryUsage> findByMenuItem(MenuItem menuItem);
}
