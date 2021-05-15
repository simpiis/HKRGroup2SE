package com.example.hkrgroup2se.Skeleton;

public class ShoppingListItem {
    String name;
    String amount;

    ShoppingListItem(String name, String amount){
        this.name=name;
        this.amount=amount;
    }

    public String getName(){
        return name;
    }
    public String getAmount(){
        return amount;
    }
}
