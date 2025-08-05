package com.billingcounter.service;

import com.billingcounter.dto.BillPaymentRequest;
import com.billingcounter.dto.BillRequestDTO;
import com.billingcounter.dto.DailyReportDTO;
import com.billingcounter.model.Bill;
import com.billingcounter.model.BillItem;
import com.billingcounter.model.HotelTable;
import com.billingcounter.model.MenuItem;
import com.billingcounter.repository.BillItemRepository;
import com.billingcounter.repository.BillRepository;
import com.billingcounter.repository.HotelTableRepository;
import com.billingcounter.repository.MenuItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * SERVICE LAYER: BillService
 * - Handles business logic for creating and retrieving bills.
 * - Calculates subtotal, tax, discount, and total for a list of items.
 */
@Service
public class BillService {

    @Autowired
    private HotelTableRepository tableRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private BillItemRepository billItemRepository;

    @Autowired
    private GroceryService groceryService;


    /**
     * Creates a new bill from list of items and tax %
     * 
     * @param itemRequests List of DTOs for items
     * @param taxPercentage Tax % to apply
     * @return saved Bill entity
     */
    public Bill createBillWithItems(List<BillRequestDTO.BillItemRequest> itemRequests, float taxPercentage) {
        BigDecimal subtotal = BigDecimal.ZERO;
        List<BillItem> billItems = new ArrayList<>();

        for (BillRequestDTO.BillItemRequest dtoItem : itemRequests) {
            MenuItem menuItem = menuItemRepository.findById(dtoItem.getItemId())
            .orElseThrow(() -> new RuntimeException("Menu item not found with ID: " + dtoItem.getItemId()));

                BillItem item = new BillItem();
                item.setItem(menuItem);
                item.setQuantity(dtoItem.getQuantity());
                item.setPrice(menuItem.getPrice().doubleValue());

                BigDecimal itemTotal = menuItem.getPrice().multiply(BigDecimal.valueOf(dtoItem.getQuantity()));
                subtotal = subtotal.add(itemTotal);

                // Deduct groceries used
                groceryService.deductIngredientsForMenuItem(menuItem, dtoItem.getQuantity());

                billItems.add(item);
        }


        // Prepare values for discount and tax
        BigDecimal discountAmount = BigDecimal.ZERO; // Can be made dynamic
        BigDecimal taxPct = BigDecimal.valueOf(taxPercentage);
        BigDecimal taxAmount = subtotal.multiply(taxPct).divide(BigDecimal.valueOf(100));
        BigDecimal totalAmount = subtotal.add(taxAmount).subtract(discountAmount);

        // Create and save Bill
        Bill bill = new Bill();
        bill.setSubtotal(subtotal.doubleValue());
        bill.setTaxPercentage(taxPct.floatValue());
        bill.setDiscountAmount(discountAmount);
        bill.setTaxAmount(taxAmount);
        bill.setTotalAmount(totalAmount.doubleValue()); // because your Bill.java still uses double
        bill.setPaid(false);

        bill = billRepository.save(bill);

        // Link and save bill items
        for (BillItem item : billItems) {
            item.setBill(bill);
        }
        billItemRepository.saveAll(billItems);

        return bill;
        }

    /**
     * Returns all bills from DB
     */
    public List<Bill> getAllBills() {
        return billRepository.findAll();
        }

    /**
     * Fetch bill by ID or throw exception
     */
    public Bill getBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        }

    /**
     * Processes payment for a bill
     */
    public Bill makePayment(Long billId, BillPaymentRequest paymentRequest) {
        // Step 1: Fetch the bill
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + billId));

        // Step 2: Set payment details
        bill.setAmountPaid(paymentRequest.getAmountPaid());
        bill.setPaymentTime(LocalDateTime.now());
        bill.setPaid(true);
        //bill.setPaymentMode(paymentRequest.getPaymentMode()); // Uncomment when implemented

        // Step 3: Free the table
        HotelTable table = bill.getTable();
        if (table != null) {
            table.setTableStatus("free");
            tableRepository.save(table);
        }

        // Step 4: Save and return updated bill
        return billRepository.save(bill);
        }

    public DailyReportDTO getDailySummary(LocalDateTime date) {
    LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
    LocalDateTime end = start.plusDays(1);

    List<Bill> bills = billRepository.findPaidBillsBetween(start, end);

    BigDecimal totalSales = BigDecimal.ZERO;
    BigDecimal totalTax = BigDecimal.ZERO;
    BigDecimal totalDiscount = BigDecimal.ZERO;

    for (Bill bill : bills) {
        totalSales = totalSales.add(BigDecimal.valueOf(bill.getTotalAmount()));
        if (bill.getTaxAmount() != null)
            totalTax = totalTax.add(bill.getTaxAmount());
        if (bill.getDiscountAmount() != null)
            totalDiscount = totalDiscount.add(bill.getDiscountAmount());
    }

    DailyReportDTO summary = new DailyReportDTO();
    summary.setTotalSales(totalSales);
    summary.setTotalTax(totalTax);
    summary.setTotalDiscount(totalDiscount);
    summary.setBillCount(bills.size());

    return summary;
    }

    public DailyReportDTO getSummaryBetween(LocalDateTime start, LocalDateTime end) {
    List<Bill> bills = billRepository.findPaidBillsBetween(start, end);

    BigDecimal totalSales = BigDecimal.ZERO;
    BigDecimal totalTax = BigDecimal.ZERO;
    BigDecimal totalDiscount = BigDecimal.ZERO;

    for (Bill bill : bills) {
        totalSales = totalSales.add(BigDecimal.valueOf(bill.getTotalAmount()));
        if (bill.getTaxAmount() != null)
            totalTax = totalTax.add(bill.getTaxAmount());
        if (bill.getDiscountAmount() != null)
            totalDiscount = totalDiscount.add(bill.getDiscountAmount());
    }

    DailyReportDTO summary = new DailyReportDTO();
    summary.setTotalSales(totalSales);
    summary.setTotalTax(totalTax);
    summary.setTotalDiscount(totalDiscount);
    summary.setBillCount(bills.size());

    return summary;
    }

}
