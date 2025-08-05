package com.billingcounter.service;

import com.billingcounter.model.Bill;
import com.billingcounter.model.BillItem;
import com.billingcounter.repository.BillItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE: BillItemService
 * Manages line items in each bill (what was ordered, quantity, price).
 *
 * Interview Tip:
 * - This layer separates controller logic from business logic.
 * - Useful for subtotal calculation, analytics, etc.
 */

@Service
public class BillItemService {

    @Autowired
    private BillItemRepository billItemRepository;

    // Add a new bill item
    public BillItem addBillItem(BillItem item) {
        return billItemRepository.save(item);
    }

    // Get all bill items
    public List<BillItem> getAllBillItems() {
        return billItemRepository.findAll();
    }

    // Get a single bill item by ID
    public Optional<BillItem> getBillItemById(Long id) {
        return billItemRepository.findById(id);
    }

    // Get all items under a specific bill
    public List<BillItem> getItemsByBill(Bill bill) {
        return billItemRepository.findByBill(bill);
    }

    // Update an existing bill item
    public Optional<BillItem> updateBillItem(Long id, BillItem updatedItem) {
        return billItemRepository.findById(id).map(existing -> {
            existing.setItem(updatedItem.getItem());
            existing.setQuantity(updatedItem.getQuantity());
            existing.setPrice(updatedItem.getPrice());
            return billItemRepository.save(existing);
        });
    }

    // Delete bill item by ID
    public void deleteBillItem(Long id) {
        billItemRepository.deleteById(id);
    }
}
