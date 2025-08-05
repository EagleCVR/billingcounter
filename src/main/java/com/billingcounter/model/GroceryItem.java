package com.billingcounter.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * ENTITY CLASS: GroceryItem
 * This class maps to the 'groceries' table in PostgreSQL using JPA.
 * Each instance of this class represents a row in the table.
 *
 * Interview Note:
 * - `@Entity` tells Spring JPA this is a persistent entity.
 * - `@Table(name = "groceries")` maps this class to the actual table.
 */
@Entity
@Table(name = "groceries")
public class GroceryItem {

    // Primary key auto-incremented by database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the item (cannot be null)
    @Column(name = "item_name", nullable = false)
    private String itemName;

    // Quantity in stock
    @Column(nullable = false)
    private int quantity;

    // Unit like kg, litre, packet
    @Column
    private String unit;

    // Alert threshold to remind restocking
    @Column(nullable = false)
    private int threshold;

    // Timestamp when last updated (auto managed by DB default)
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Getters and Setters (required by JPA)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
