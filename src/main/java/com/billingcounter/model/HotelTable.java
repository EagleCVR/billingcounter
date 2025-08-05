package com.billingcounter.model;

import javax.persistence.*;
/**
 * MODEL LAYER: HotelTable
 * Represents physical tables in the hotel (for seating and ordering)
 *
 * Interview Note:
 * - `@Entity` makes this class a JPA-managed table
 * - Used to manage table availability & status during billing
 */
@Entity
@Table(name="hotel_tables")
public class HotelTable {
    // Primary key with auto generated ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Table number (displayed to waiter/staff)
    @Column(nullable = false, unique = true)
    private int tableNumber;

    // Table Status (Free/Occupied) Default Free
    @Column(nullable = false, name = "status")
    private String tableStatus = "free";

    //Optional Remarks
    private String description;

    // Getters and Setters

    public Long getId() {return id;}
    
    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) {this.tableNumber = tableNumber;}

    public String getTableStatus() {return tableStatus;}
    public void setTableStatus(String status) {this.tableStatus = status;}

    public String getDescription() {return description;}
    public void setDescription(String description){this.description = description;}

}
