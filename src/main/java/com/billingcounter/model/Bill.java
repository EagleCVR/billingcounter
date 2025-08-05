package com.billingcounter.model;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double subtotal;

    @Column(name = "tax_percentage")
    private float taxPercentage;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private boolean paid = false;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime paymentTime;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "tax_amount")
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "amount_paid")
    private BigDecimal amountPaid = BigDecimal.ZERO;



    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    @ManyToOne
    @JoinColumn(name = "table_id") // optional: defines foreign key name
    private HotelTable table;

    public HotelTable getTable() {
    return table;
    }

    public void setTable(HotelTable table) {
        this.table = table;
    }

    public Long getId() { return id; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public float getTaxPercentage() { return taxPercentage; }
    public void setTaxPercentage(float taxPercentage) { this.taxPercentage = taxPercentage; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
    public BigDecimal getAmountPaid() { return amountPaid; }
    public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }

}