// Package helps organize classes logically. Here, it's in the `service` layer of our app.
package com.billingcounter.service;

// Model represents our DB entity (like a table row).
import com.billingcounter.model.GroceryItem;

// Repository interacts with the database via Spring Data JPA.
import com.billingcounter.repository.GroceryRepository;

// Allows Spring to auto-inject (provide) dependencies.
import org.springframework.beans.factory.annotation.Autowired;

// Tells Spring this is a service class and should be managed as a bean.
import org.springframework.stereotype.Service;

import com.billingcounter.model.MenuItem;
import com.billingcounter.model.MenuItemGroceryUsage;
import com.billingcounter.repository.MenuItemGroceryUsageRepository;
import java.math.BigDecimal;


import java.util.List;
import java.util.Optional;

/**
 * ✅ SERVICE LAYER: GroceryService
 *
 * 📌 PURPOSE:
 * - Contains **business logic** (rules, calculations, workflows).
 * - Called by the Controller layer (not directly by the user).
 * - Delegates DB interaction to the Repository layer.
 *
 * 🧠 INTERVIEW TIP:
 * - Service layer helps make the code modular, testable, and maintainable.
 * - Follows the **Separation of Concerns (SoC)** design principle.
 * - Standard part of the **3-tier architecture** (Controller → Service → Repository).
 */
@Service // Marks this as a service component (Spring manages its lifecycle)
public class GroceryService {

    @Autowired // Automatically injects GroceryRepository bean into this service
    private GroceryRepository groceryRepository;

    @Autowired
    private MenuItemGroceryUsageRepository usageRepository;


    // ✅ GET all groceries from DB
    public List<GroceryItem> getAllGroceries() {
        return groceryRepository.findAll(); // Fetches all rows
    }

    // ✅ GET grocery item by its ID
    public Optional<GroceryItem> getGroceryById(Long id) {
        return groceryRepository.findById(id); // Returns Optional (may or may not exist)
    }

    // ✅ ADD a new grocery item
    public GroceryItem addGrocery(GroceryItem item) {
        return groceryRepository.save(item); // Saves to DB (insert)
    }

    // ✅ UPDATE grocery item by ID
    public Optional<GroceryItem> updateGrocery(Long id, GroceryItem updatedItem) {
        return groceryRepository.findById(id).map(existingItem -> {
            // Update fields manually (helps avoid overwriting unintended data)
            existingItem.setItemName(updatedItem.getItemName());
            existingItem.setQuantity(updatedItem.getQuantity());
            existingItem.setThreshold(updatedItem.getThreshold());
            existingItem.setUnit(updatedItem.getUnit());

            return groceryRepository.save(existingItem); // Saves updated data (update)
        });
    }

    // ✅ DELETE item by ID
    public void deleteGrocery(Long id) {
        groceryRepository.deleteById(id);
    }

    // ✅ LIST items below threshold for refill alert
    public List<GroceryItem> getLowStockItems() {
        return groceryRepository.findByQuantityLessThan(10); // Custom finder method
    }

    public void deductIngredientsForMenuItem(MenuItem menuItem, int quantityOrdered) {
    List<MenuItemGroceryUsage> usages = usageRepository.findByMenuItem(menuItem);

    for (MenuItemGroceryUsage usage : usages) {
        GroceryItem grocery = usage.getGroceryItem();
        BigDecimal usedTotal = usage.getQuantityUsed().multiply(BigDecimal.valueOf(quantityOrdered));
        int remainingQty = grocery.getQuantity() - usedTotal.intValue();

        grocery.setQuantity(Math.max(remainingQty, 0));
        groceryRepository.save(grocery);
    }
}

}
