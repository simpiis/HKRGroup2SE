package com.example.hkrgroup2se.Skeleton;

import java.util.ArrayList;

public class Inventory {
    private static Inventory singleInstance = null;
    private ArrayList<Grocery> inventory;

    public ArrayList<Grocery> getInventory() {
        return inventory;
    }

    public void addGrocery(Grocery grocery){
        inventory.add(grocery);
    }

    public static Inventory getInstance(){
        if(singleInstance == null){
            singleInstance = new Inventory();
        }
        return singleInstance;
    }

    private Inventory(){
        inventory = new ArrayList<>();
    }
}
