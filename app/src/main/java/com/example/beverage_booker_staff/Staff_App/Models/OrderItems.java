package com.example.beverage_booker_staff.Staff_App.Models;

public class OrderItems {

    private int orderID;
    private int cartID;
    private String Status;

    public OrderItems(int orderID, int cartID, String status) {
        this.orderID = orderID;
        this.cartID = cartID;
        this.Status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getCartID() {
        return cartID;
    }

    public String getStatus() {
        return Status;
    }

}
