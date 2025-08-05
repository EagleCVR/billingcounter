package com.billingcounter.repository;

import com.billingcounter.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT b FROM Bill b WHERE b.paid = true AND b.paymentTime BETWEEN :start AND :end")
    List<Bill> findPaidBillsBetween(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
}
