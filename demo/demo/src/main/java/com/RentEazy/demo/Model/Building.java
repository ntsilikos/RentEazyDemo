package com.RentEazy.demo.Model;

import java.util.List;

public class Building {
    private String address;
    private int numUnits;

    public Building(String address, int numUnits){
        this.address = address;
        this.numUnits = numUnits;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public void setNumUnits(int numUnits) {
        this.numUnits = numUnits;
    }


}
