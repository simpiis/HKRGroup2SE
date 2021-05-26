package com.example.hkrgroup2se.Skeleton;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class WasteTest {
    // String wasteDate, double priceOfWaste, String amountOfWaste, String typeOfAmountForWaste

    Waste waste = new Waste("07-10-2020",(double) 99.02, "100", "gram");

    @Test
    public void getWasteDate() {
        String expectedValue = "07-10-2020";
        String realValue = waste.getWasteDate();
        Assert.assertEquals(expectedValue,realValue);
    }

    @Test
    public void getPriceOfWaste() {
        double expectedValue = 99.02;
        double realValue = waste.getPriceOfWaste();
        Assert.assertEquals(expectedValue,realValue,0);
    }

    @Test
    public void getAmountOfWaste() {
        String expectedValue = "100";
        String realValue = waste.getAmountOfWaste();
        Assert.assertEquals(expectedValue,realValue);
    }

    @Test
    public void getTypeOfAmountForWaste() {
        String expectedValue = "gram";
        String realValue = waste.getTypeOfAmountForWaste();
        Assert.assertEquals(expectedValue,realValue);
    }
}