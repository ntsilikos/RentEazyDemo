package com.RentEazy.demo.Model;

public class Unit {
    private int numberOfUnits;
    private int numBedrooms;
    private int numBathrooms;
    private int squareFootage;
    private boolean isAvailable;

    public Unit(int unitNumber, int numBedrooms, int numBathrooms, int squareFootage, boolean isAvailable) {
        this.numberOfUnits = unitNumber;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.squareFootage = squareFootage;
        this.isAvailable = isAvailable;
    }

    public int getUnitNumber() {
        return numberOfUnits;
    }

    public void setUnitNumber(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public int getNumBedrooms() {
        return numBedrooms;
    }

    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public int getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(int squareFootage) {
        this.squareFootage = squareFootage;
    }



    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
