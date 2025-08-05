package com.billingcounter.service;

import com.billingcounter.model.MenuItem;
import com.billingcounter.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE: MenuItemService
 * Handles business logic for menu items in the restaurant.
 * Includes CRUD operations and custom availability/category filters.
 *
 * Interview Tip:
 * - Service layer abstracts business rules away from controllers.
 * - Encourages reusability and separation of concerns.
 */

@Service
public class MenuItemService {

    // Inject MenuItemRepository to interact with the database
    @Autowired
    private MenuItemRepository menuItemRepository;

    // Add a new menu item
    public MenuItem addMenuItem(MenuItem item) {
        return menuItemRepository.save(item);
    }

    // Get all menu items
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    // Get a single menu item by ID
    public Optional<MenuItem> getMenuItemById(Long id) {
        return menuItemRepository.findById(id);
    }

    // Update an existing menu item
    public Optional<MenuItem> updateMenuItem(Long id, MenuItem updatedItem) {
        return menuItemRepository.findById(id).map(existing -> {
            existing.setName(updatedItem.getName());
            existing.setCategory(updatedItem.getCategory());
            existing.setPrice(updatedItem.getPrice());
            existing.setAvailable(updatedItem.isAvailable());
            return menuItemRepository.save(existing);
        });
    }

    // Delete a menu item by ID
    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    // Get all available items (isAvailable = true)
    public List<MenuItem> getAvailableItems() {
        return menuItemRepository.findByAvailableTrue();
    }

    // Get items by category
    public List<MenuItem> getItemsByCategory(String category) {
        return menuItemRepository.findByCategory(category);
    }
}
