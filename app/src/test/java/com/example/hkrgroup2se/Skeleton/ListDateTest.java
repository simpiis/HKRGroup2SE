package com.example.hkrgroup2se.Skeleton;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListDateTest {

    ListDate date = new ListDate("2020-05-02");

    @Test
    public void getDate() {
        String expectedValue = "2020-05-02";
        String realValue = date.getDate();
        Assert.assertEquals(expectedValue,realValue);

    }
}