package com.billingcounter.controller;

import com.billingcounter.dto.BillPaymentRequest;
import com.billingcounter.dto.BillRequestDTO;
import com.billingcounter.dto.DailyReportDTO;
import com.billingcounter.model.Bill;
import com.billingcounter.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    // POST: Create new bill with items
    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody BillRequestDTO request) {
        Bill created = billService.createBillWithItems(request.getItems(), request.getTaxPercentage());
        return ResponseEntity.status(201).body(created);
    }

    // GET: Fetch all bills
    @GetMapping
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    // GET: Fetch a bill by ID
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(billService.getBillById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/pay")
        public Bill payBill(@PathVariable Long id, @RequestBody BillPaymentRequest paymentRequest) {
        return billService.makePayment(id, paymentRequest);
    }

    @GetMapping("/summary/today")
    public ResponseEntity<DailyReportDTO> getTodaySummary() {
        LocalDateTime today = LocalDateTime.now();
        return ResponseEntity.ok(billService.getDailySummary(today));
    }

    // GET: Summary between custom date range
    @GetMapping("/summary")
    public ResponseEntity<DailyReportDTO> getSummaryBetween(
        @RequestParam("from") String from,
        @RequestParam("to") String to) {

    try {
        LocalDateTime start = LocalDateTime.parse(from);
        LocalDateTime end = LocalDateTime.parse(to);
        DailyReportDTO summary = billService.getSummaryBetween(start, end);
        return ResponseEntity.ok(summary);
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}


}