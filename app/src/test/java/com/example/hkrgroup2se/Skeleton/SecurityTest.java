package com.example.hkrgroup2se.Skeleton;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class SecurityTest {
    Security sec = new Security();

    @Test
    public void checkDate() {
        LocalDate expectedValue = LocalDate.parse("2020-05-02");
        LocalDate realValue = sec.checkDate("2","5","2020");
        Assert.assertEquals(expectedValue.toString(),realValue.toString());

    }

    @Test
    public void checkString() {
        boolean realValue = sec.checkString("lsdjfasldöfjlaewfjlwaefjrlafjsdfjsalöfjsavjlasjflsd");
        Assert.assertTrue(realValue);

        boolean realValueFalse = sec.checkString("123213123213123213123");
        Assert.assertFalse(realValueFalse);

    }

    @Test
    public void checkEmail() {
        boolean realValue = sec.checkEmail("mats@ludanger.com");
        Assert.assertTrue(realValue);

        boolean realValueFalse = sec.checkEmail("mats@ludanger,com");
        Assert.assertFalse(realValueFalse);

    }

    @Test
    public void hashString() {
        String expectedValue = "d26ad533eca54f6e7f6e7eb180d55ed878af8eae699f9044eeef6aef2dcc7";
        String realValue = sec.hashString("mats_06_20@hotmail.com");
        Assert.assertEquals(expectedValue,realValue);
    }
}