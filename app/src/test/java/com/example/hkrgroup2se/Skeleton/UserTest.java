package com.example.hkrgroup2se.Skeleton;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {


    // String firstName, String lastName, String email, String userUID

    User user = new User("Mats","Lundager","mats_06_20@hotmail.com","id123");

    @Test
    public void getFirstName() {
        String expectedValue = "Mats";
        String realValue = user.getFirstName();
        Assert.assertEquals(expectedValue,realValue);

    }

    @Test
    public void setFirstName() {
        // relies on getFirsName
        String expectedValue = "Richard";
        user.setFirstName("Richard");
        String realValue = user.getFirstName();
        Assert.assertEquals(expectedValue,realValue);

    }

    @Test
    public void getLastName() {
        String expectedValue = "Lundager";
        String realValue = user.getLastName();
        Assert.assertEquals(expectedValue,realValue);

    }

    @Test
    public void setLastName() {
        // relies on getLastName
        String expectedValue = "Svensson";
        user.setLastName("Svensson");
        String realValue = user.getLastName();
        Assert.assertEquals(expectedValue,realValue);
    }

    @Test
    public void getEmail() {

        String expectedValue = "mats_06_20@hotmail.com";
        String realValue = user.getEmail();
        Assert.assertEquals(expectedValue,realValue);

    }

    @Test
    public void setEmail() {
        // relies on getEmail
        String expectedValue = "richard.svensson@gmail.com";
        user.setEmail("richard.svensson@gmail.com");
        String realValue = user.getEmail();
        Assert.assertEquals(expectedValue,realValue);
    }

    @Test
    public void getUserUID() {
        String expectedValue = "id123";
        String realValue = user.getUserUID();
        Assert.assertEquals(expectedValue,realValue);
    }

    @Test
    public void setUserUID() {
        // relies on getUserUID
        String expectedValue = "id111";
        user.setUserUID("id111");
        String realValue = user.getUserUID();
        Assert.assertEquals(expectedValue,realValue);
    }
}