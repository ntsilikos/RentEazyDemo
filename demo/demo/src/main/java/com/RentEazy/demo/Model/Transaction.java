package com.RentEazy.demo.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

    private int transactionId;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private boolean forCurrentMonth;
    private boolean isLatePayment;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

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

    public boolean isForCurrentMonth() {
        return forCurrentMonth;
    }

    public void setForCurrentMonth(boolean forCurrentMonth) {
        this.forCurrentMonth = forCurrentMonth;
    }

    public boolean isLatePayment() {
        return isLatePayment;
    }

    public void setLatePayment(boolean latePayment) {
        isLatePayment = latePayment;
    }


}
