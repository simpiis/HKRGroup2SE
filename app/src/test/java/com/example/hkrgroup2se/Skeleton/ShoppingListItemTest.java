package com.example.hkrgroup2se.Skeleton;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShoppingListItemTest {

    //String itemName, String itemAmount,String itemComment

    ShoppingListItem item = new ShoppingListItem("Köttfärs","500","Till taco kvällen");

    @Test
    public void getItemName() {
        String expectedValue = "Köttfärs";
        String realValue = item.getItemName();
        Assert.assertEquals(expectedValue,realValue);
    }

    @Test
    public void getItemAmount() {
        String expectedValue = "500";
        String realValue = item.getItemAmount();
        Assert.assertEquals(expectedValue,realValue);
    }

    @Test
    public void getItemComment() {
        String expectedValue = "Till taco kvällen";
        String realValue = item.getItemComment();
        Assert.assertEquals(expectedValue,realValue);
    }
}