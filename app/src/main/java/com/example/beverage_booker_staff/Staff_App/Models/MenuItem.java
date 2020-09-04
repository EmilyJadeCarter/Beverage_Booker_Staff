package com.example.beverage_booker_staff.Staff_App.Models;

public class MenuItem {

    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int milk;
    private int sugar;
    private int decaf;
    private int extras;
    private int frappe;
    private int heated;
    private String itemType;

    public MenuItem(int id, String name, String description, double price, int quantity,
                    int milk, int sugar, int decaf, int extras, int frappe, int heated, String itemType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.milk = milk;
        this.sugar = sugar;
        this.decaf = decaf;
        this.extras = extras;
        this.frappe = frappe;
        this.heated = heated;
        this.itemType = itemType;
    }

    public MenuItem(int id, String name, String description, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public MenuItem(int id, double price) {
        this.id = id;
        this.price = price;
    }

    public MenuItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public int getMilk() {
        return milk;
    }
    public int getSugar() {
        return sugar;
    }
    public int getDecaf() {
        return decaf;
    }
    public int getExtras() {
        return extras;
    }
    public int getFrappe() {
        return frappe;
    }
    public int getHeated() {
        return heated;
    }
    public String getItemType() {
        return itemType;
    }


    //Quantity methods
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
