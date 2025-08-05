package com.billingcounter.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * ENTITY: MenuItem
 * Represents a dish or item in the restaurant's menu.
 * Maps to `menu_items` table in the database.
 *
 * Interview Tip:
 * - @Entity maps Java class to DB table
 * - @Table allows custom table name
 * - @Column allows customization per field
 */

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primary key auto-increment
    private Long id;

    @Column(nullable = false)
    private String name; // Name of the menu item (e.g., "Butter Naan")

    @Column(nullable = false)
    private BigDecimal price; // Price of the item

    @Column(nullable = true)
    private String category; // Optional (e.g., "Main Course", "Beverages")

    @Column(name = "is_available", nullable = false)
    private boolean available = true; // Availability status (default = true)

    // ------------------- GETTERS & SETTERS ------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
