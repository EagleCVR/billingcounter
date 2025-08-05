package com.billingcounter.dto;

import java.math.BigDecimal;

public class BillPaymentRequest {
    private BigDecimal amountPaid;
    private String paymentMode;

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}
