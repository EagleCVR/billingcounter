package com.billingcounter.controller;

import com.billingcounter.model.BillItem;
import com.billingcounter.service.BillItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST CONTROLLER: BillItemController
 *
 * Handles HTTP operations for bill line items.
 * Every item in the bill has quantity, price, and item info.
 *
 * Interview Tip:
 * - Helps manage nested/related resources
 * - Works with Bill entity as foreign key
 */

@RestController
@RequestMapping("/api/bill-items")
public class BillItemController {

    @Autowired
    private BillItemService billItemService;

    // Get all bill items
    @GetMapping
    public List<BillItem> getAllBillItems() {
        return billItemService.getAllBillItems();
    }

    // Get a specific bill item
    @GetMapping("/{id}")
    public ResponseEntity<BillItem> getBillItemById(@PathVariable Long id) {
        return billItemService.getBillItemById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Add new bill item
    @PostMapping
    public ResponseEntity<BillItem> addBillItem(@RequestBody BillItem item) {
        BillItem savedItem = billItemService.addBillItem(item);
        return ResponseEntity.status(201).body(savedItem);
    }

    // Update bill item
    @PutMapping("/{id}")
    public ResponseEntity<BillItem> updateBillItem(@PathVariable Long id, @RequestBody BillItem updatedItem) {
        return billItemService.updateBillItem(id, updatedItem)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Delete bill item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBillItem(@PathVariable Long id) {
        billItemService.deleteBillItem(id);
        return ResponseEntity.noContent().build();
    }
}
