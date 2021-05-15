package com.example.hkrgroup2se.Skeleton;

public class Waste {

    private String wasteDate;
    private double priceOfWaste;
    private String amountOfWaste;
    private String typeOfAmountForWaste;


    public Waste(String wasteDate, double priceOfWaste, String amountOfWaste, String typeOfAmountForWaste) {
        this.wasteDate = wasteDate;
        this.priceOfWaste = priceOfWaste;
        this.amountOfWaste = amountOfWaste;
        this.typeOfAmountForWaste = typeOfAmountForWaste;
    }

    public Waste(){

    }

    public String getWasteDate() {
        return wasteDate;
    }

    public double getPriceOfWaste() {
        return priceOfWaste;
    }

    public String getAmountOfWaste() {
        return amountOfWaste;
    }

    public String getTypeOfAmountForWaste() {
        return typeOfAmountForWaste;
    }
}

