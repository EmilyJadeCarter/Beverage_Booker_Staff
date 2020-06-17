package com.example.beverage_booker_staff.Staff_App.Models;

public class CartItems {

    private int itemID;
    private String itemTitle;
    private int quantity;


    public CartItems(int itemID, String itemTitle, int itemQuantity){

        this.itemID = itemID;
        this.itemTitle = itemTitle;
        this.quantity = quantity;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public int getQuantity() {
        return quantity;
    }
}

