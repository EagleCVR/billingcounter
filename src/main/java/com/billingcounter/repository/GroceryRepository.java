package com.billingcounter.repository;

import com.billingcounter.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ✅ REPOSITORY LAYER: GroceryRepository
 *
 * This interface handles all CRUD operations for GroceryItem.
 * It extends JpaRepository, which provides built-in methods like:
 * - findAll()
 * - findById()
 * - save()
 * - deleteById()
 * - etc.
 *
 * 🧠 Interview Ready Note:
 * - Spring Data JPA automatically generates implementations at runtime.
 * - No need to write SQL or implementation classes.
 */
@Repository
public interface GroceryRepository extends JpaRepository<GroceryItem, Long> {

    // 🧠 Custom finder method: gets items below threshold (for refill alerts)
    List<GroceryItem> findByQuantityLessThan(int threshold);
}
