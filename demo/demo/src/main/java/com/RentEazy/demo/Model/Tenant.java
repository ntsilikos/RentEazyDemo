package com.RentEazy.demo.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Tenant {
    private String tenantName;
    private String tenantPhone;
    private BigDecimal monthlyPayment;
    private BigDecimal currentRentOwed;
    private boolean hasPet;
    private LocalDate moveInDate;

    public Tenant() {
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantPhone() {
        return tenantPhone;
    }

    public void setTenantPhone(String tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public BigDecimal getCurrentRentOwed() {
        return currentRentOwed;
    }

    public void setCurrentRentOwed(BigDecimal currentRentOwed) {
        this.currentRentOwed = currentRentOwed;
    }

    public boolean isHasPet() {
        return hasPet;
    }

    public void setHasPet(boolean hasPet) {
        this.hasPet = hasPet;
    }

    public LocalDate getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(LocalDate moveInDate) {
        this.moveInDate = moveInDate;
    }
}
