package com.example.beverage_booker_staff.Staff_App.Models;

public class CartItems {

    private int itemID;
    private String itemTitle;
    private int quantity;

    private String itemMilk;
    private String itemSugar;
    private String itemDecaf;
    private String itemVanilla;
    private String itemCaramel;
    private String itemChocolate;
    private String itemWhippedCream;
    private String itemFrappe;
    private String itemHeated;
    private String itemComment;

    public CartItems(int itemID, String itemTitle, int quantity, String itemMilk, String itemSugar){

        this.itemID = itemID;
        this.itemTitle = itemTitle;
        this.quantity = quantity;
        this.itemMilk = itemMilk;
        this.itemSugar = itemSugar;
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

    public String getItemMilk() {
        return itemMilk;
    }

    public String getItemSugar() {
        return itemSugar;
    }

    public String getItemDecaf() {
        return itemDecaf;
    }

    public String getItemVanilla() {
        return itemVanilla;
    }

    public String getItemCaramel() {
        return itemCaramel;
    }

    public String getItemChocolate() {
        return itemChocolate;
    }

    public String getItemWhippedCream() {
        return itemWhippedCream;
    }

    public String getItemFrappe() {
        return itemFrappe;
    }

    public String getItemHeated() {
        return itemHeated;
    }

    public String getItemComment() {
        return itemComment;
    }

}

