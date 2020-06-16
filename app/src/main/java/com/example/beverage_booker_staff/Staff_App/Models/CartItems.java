package com.example.beverage_booker_staff.Staff_App.Models;

public class CartItems {

    private int itemID;
    private String itemTitle;
    private int itemQuantity;


    public CartItems(int id, String title, int quantity){

        this.itemID = id;
        this.itemTitle = title;
        this.itemQuantity = quantity;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }
}

