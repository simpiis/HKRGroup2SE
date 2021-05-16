package com.example.hkrgroup2se.Skeleton;

public class ShoppingListItem {
    String itemName;
    String itemAmount;
    String itemComment;

    ShoppingListItem(String itemName, String itemAmount,String itemComment){
        this.itemName=itemName;
        this.itemAmount=itemAmount;
        this.itemComment=itemComment;
    }
    ShoppingListItem(){

    }

    public String getItemName() {
        return itemName;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public String getItemComment() {
        return itemComment;
    }
}
