package com.example.hkrgroup2se.Skeleton;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class InventoryTest {

    float bogge =  99.99f;
    Grocery grocery = new Grocery("Köttfärs","ica basic",bogge,"gram","500","kött fisk & fågel","07-09-2020");
    Inventory inventory = Inventory.getInstance();


    @Test
    public void getInventory() {
        ArrayList<Grocery> expectedValue = new ArrayList<>();
        expectedValue.add(grocery);
        inventory.addGrocery(grocery);
        ArrayList<Grocery> realValue = inventory.getInventory();
        Assert.assertEquals(expectedValue,realValue);

    }

    @Test
    public void addGrocery() {

    }

    @Test
    public void getInstance() {
        Inventory instance1 = Inventory.getInstance();
        Inventory instance2 = Inventory.getInstance();
        Assert.assertEquals(instance1,instance2);
    }
}