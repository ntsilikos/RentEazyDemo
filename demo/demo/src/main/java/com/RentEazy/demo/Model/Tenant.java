package com.RentEazy.demo.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Tenant {
    private int tenantId;
    private String tenantName;
    private String tenantPhone;
    private BigDecimal monthlyPayment;
    private BigDecimal currentRentOwed;
    private BigDecimal securityDeposit;
    private boolean hasPet;
    private LocalDate moveInDate;
    private boolean currentMonthPaid;
    private String building;
    private String unit_number;

    public Tenant() {
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
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

    public BigDecimal getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(BigDecimal securityDeposit) {
        this.securityDeposit = securityDeposit;
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


    public boolean isCurrentMonthPaid() {
        return currentMonthPaid;
    }

    public void setCurrentMonthPaid(boolean currentMonthPaid) {
        this.currentMonthPaid = currentMonthPaid;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(String unit_number) {
        this.unit_number = unit_number;
    }

}
