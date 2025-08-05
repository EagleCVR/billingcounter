package com.billingcounter.repository;

import com.billingcounter.model.BillItem;
import com.billingcounter.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository: BillItemRepository
 * Provides CRUD operations for items linked to a bill.
 */

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Long> {

    // Find all items for a specific bill
    List<BillItem> findByBill(Bill bill);
}
