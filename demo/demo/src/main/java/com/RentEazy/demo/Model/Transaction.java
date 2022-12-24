package com.RentEazy.demo.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

    private LocalDate paymentDate;
    private BigDecimal amount;

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
