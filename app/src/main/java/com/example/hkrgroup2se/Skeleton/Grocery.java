package com.example.hkrgroup2se.Skeleton;

import java.time.LocalDate;

public class Grocery {
    private String name;
    private String brand;
    private float prize;
    private String typeOfAmount;
    private String amount;
    private String typeOfGrocery;
    private String bestBefore;


    public Grocery(String name, String brand, float prize, String typeOfAmount, String amount, String typeOfGrocery, String bestBefore) {
        this.name = name;
        this.brand = brand;
        this.prize = prize;
        this.typeOfAmount = typeOfAmount;
        this.amount = amount;
        this.typeOfGrocery = typeOfGrocery;
        this.bestBefore = bestBefore;
    }

    //Constructor for Barcode scanner (from database\Grocery\[EAN]
    public Grocery(String name, String brand, String typeOfAmount, String amount, String typeOfGrocery) {
        this.name = name;
        this.brand = brand;
        this.typeOfAmount = typeOfAmount;
        this.amount = amount;
        this.typeOfGrocery = typeOfGrocery;
    }

    public Grocery(){

    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public float getPrize() {
        return prize;
    }

    public String getTypeOfAmount() {
        return typeOfAmount;
    }

    public String getAmount() {
        return amount;
    }

    public String getTypeOfGrocery() {
        return typeOfGrocery;
    }

    public String getBestBefore() {
        return bestBefore;
    }
}
