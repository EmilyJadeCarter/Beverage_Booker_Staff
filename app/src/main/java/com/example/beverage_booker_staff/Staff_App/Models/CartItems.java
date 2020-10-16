package com.example.beverage_booker_staff.Staff_App.Models;

public class CartItems {

    private int itemID;
    private String itemTitle;
    private int quantity;
    private int itemStatus;

    private String itemSize;
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


    public CartItems(int itemID, String itemTitle, int itemQuantity) {

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

    public int getItemStatus() {
        return itemStatus;
    }

    //Gettters for menu options
    public String getItemSize() {
        return itemSize;
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

