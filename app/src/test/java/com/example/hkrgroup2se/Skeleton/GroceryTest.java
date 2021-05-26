package com.example.hkrgroup2se.Skeleton;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GroceryTest {


    // String name, String brand, float prize, String typeOfAmount, String amount, String typeOfGrocery, String bestBefore
    Grocery grocery = new Grocery("Köttfärs","ica basic",99.99f,"gram","500","kött fisk & fågel","07-09-2020");



    @Test
    public void getName() {
        String expectedValue = "Köttfärs";
        String realValue = grocery.getName();
        Assert.assertEquals(expectedValue,realValue);
    }

    @Test
    public void getBrand() {
        String expectedValue = "ica basic";
        String realValue = grocery.getBrand();
        Assert.assertEquals(expectedValue,realValue);

    }

    @Test
    public void getPrize() {
        float expectedValue = 99.99f;
        float realValue = grocery.getPrize();
        Assert.assertEquals(expectedValue,realValue,0);
    }

    @Test
    public void getTypeOfAmount() {
        String expectedValue = "gram";
        String realValue = grocery.getTypeOfAmount();
        Assert.assertEquals(expectedValue,realValue);

    }

    @Test
    public void getAmount() {
        String expectedValue = "500";
        String realValue = grocery.getAmount();
        Assert.assertEquals(expectedValue,realValue);
    }

    @Test
    public void getTypeOfGrocery() {
        String expectedValue = "kött fisk & fågel";
        String realValue = grocery.getTypeOfGrocery();
        Assert.assertEquals(expectedValue,realValue);
    }

    @Test
    public void getBestBefore() {
        String expectedValue = "07-09-2020";
        String realValue = grocery.getBestBefore();
        Assert.assertEquals(expectedValue,realValue);
    }
}