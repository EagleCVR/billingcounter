package com.billingcounter.model; // 📦 Declares the package location of this class for modular organization

// 📚 JPA and Java utility imports
import javax.persistence.*; // 📦 JPA annotations for ORM (Object Relational Mapping)
import java.time.LocalDateTime; // ⏰ Java 8 class to represent timestamps

/**
 * 📘 Grocery Entity - Represents a grocery item stored in the database.
 * 
 * 🔹 This class is part of the **Model Layer** in MVC architecture.
 * 🔹 Spring Boot + JPA uses this class to map Java objects to rows in the `groceries` table.
 * 🔹 Follows POJO (Plain Old Java Object) pattern with private fields and public getters/setters.
 */
@Entity // 🧬 Marks this class as a JPA entity so Spring can map it to a database table
@Table(name = "groceries") // 🏷️ Explicitly maps this entity to the `groceries` table in PostgreSQL
public class Grocery {

    /**
     * 🆔 Primary key field
     * @Id tells JPA this is the primary key column (usually auto-incremented)
     * @GeneratedValue allows the DB to automatically generate the ID using a specific strategy
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 🧮 Uses DB auto-increment (e.g., SERIAL in PostgreSQL)
    private Long id;

    /**
     * 🏷️ Item name of the grocery (e.g., "Rice", "Milk")
     * @Column used to map to a specific DB column, can set constraints like nullable = false
     */
    @Column(name = "item_name", nullable = false) // 🔒 Cannot be null in DB
    private String itemName;

    /**
     * 📦 Quantity of the grocery item (e.g., 5 kg)
     * Maps directly to the `quantity` column
     */
    private int quantity;

    /**
     * 🧪 Unit of measurement (e.g., kg, liter, packet)
     */
    private String unit;

    /**
     * 🚨 Threshold below which stock refill is required
     */
    private int threshold;

    /**
     * ⏰ Last updated timestamp for audit/logging
     * Automatically updated before inserting or updating the row
     */
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    /**
     * 🧠 Lifecycle callback: updates the timestamp before insert/update
     * @PrePersist = called before inserting new row
     * @PreUpdate = called before updating an existing row
     */
    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        lastUpdated = LocalDateTime.now(); // 📆 Sets current timestamp
    }

    // 🔨 Default constructor required by JPA (always include it)
    public Grocery() {}

    // 🧰 Constructor to initialize object while creating it
    public Grocery(String itemName, int quantity, String unit, int threshold) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.unit = unit;
        this.threshold = threshold;
    }

    // 📬 GETTERS & SETTERS - Required for JPA to access private fields
    // Interview Note: Spring uses JavaBean conventions (getX/setX) to bind data

    public Long getId() { return id; }

    public String getItemName() { return itemName; }

    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }

    public void setUnit(String unit) { this.unit = unit; }

    public int getThreshold() { return threshold; }

    public void setThreshold(int threshold) { this.threshold = threshold; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
}
