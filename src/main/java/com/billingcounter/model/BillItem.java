package com.billingcounter.model;

import javax.persistence.*;

/**
 * ENTITY: BillItem
 * Represents each item ordered in a bill (line item).
 * Maps to `bill_items` table in DB.
 *
 * Interview Tip:
 * - @ManyToOne used for foreign key relationships
 * - @JoinColumn defines FK columns
 */

@Entity
@Table(name = "bill_items")
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 FK to Bill (each bill can have many bill items)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    // 🔗 FK to MenuItem (item ordered)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    private MenuItem item;

    @Column(nullable = false)
    private int quantity; // Quantity of the item ordered

    @Column(nullable = false)
    private double price; // Price at the time of billing (can vary from menu)

    // ------------------- GETTERS & SETTERS ------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public MenuItem getItem() {
        return item;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
