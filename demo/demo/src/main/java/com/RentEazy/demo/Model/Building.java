package com.RentEazy.demo.Model;

import java.util.List;

public class Building {
    private String address;
    private int numUnits;
    private List<Apartment> units;

    public Building(String address, int numUnits, List<Apartment> units){
        this.address = address;
        this.numUnits = numUnits;
        this.units = units;
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

    public List<Apartment> getUnits() {
        return units;
    }

    public void setUnits(List<Apartment> units) {
        this.units = units;
    }

}
