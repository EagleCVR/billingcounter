package com.billingcounter.controller;

import com.billingcounter.model.MenuItem;
import com.billingcounter.service.MenuItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST CONTROLLER: MenuItemController
 *
 * Handles HTTP requests related to menu items.
 * Follows REST conventions using @GetMapping, @PostMapping, etc.
 *
 * Interview Tip:
 * - @RestController combines @Controller + @ResponseBody
 * - @RequestMapping sets base route (e.g., /api/menu)
 * - @PathVariable and @RequestBody are used for parameter handling
 */

@RestController
@RequestMapping("/api/menu")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    // Get all menu items
    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    // Get menu item by ID
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Create new menu item
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem item) {
        MenuItem saved = menuItemService.addMenuItem(item);
        return ResponseEntity.status(201).body(saved); // 201 Created
    }

    // Update menu item
    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem item) {
        return menuItemService.updateMenuItem(id, item)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // @PreAuthorize("hasRole('ADMIN')")
    // @PutMapping("/{id}")
    // public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody MenuItem updatedItem) {
    // // only ADMIN can update menu
    // }

    // Delete menu item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Get only available items
    @GetMapping("/available")
    public List<MenuItem> getAvailableItems() {
        return menuItemService.getAvailableItems();
    }

    // Get items by category
    @GetMapping("/category/{category}")
    public List<MenuItem> getItemsByCategory(@PathVariable String category) {
        return menuItemService.getItemsByCategory(category);
    }
}
