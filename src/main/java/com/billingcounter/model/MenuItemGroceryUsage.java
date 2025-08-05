package com.billingcounter.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_item_grocery_usage")
public class MenuItemGroceryUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to menu item
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    // Reference to grocery item
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grocery_item_id", nullable = false)
    private GroceryItem groceryItem;

    // Quantity of grocery used per one menu item unit
    @Column(nullable = false)
    private BigDecimal quantityUsed;

    public Long getId() {
        return id;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public GroceryItem getGroceryItem() {
        return groceryItem;
    }

    public void setGroceryItem(GroceryItem groceryItem) {
        this.groceryItem = groceryItem;
    }

    public BigDecimal getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(BigDecimal quantityUsed) {
        this.quantityUsed = quantityUsed;
    }
}
