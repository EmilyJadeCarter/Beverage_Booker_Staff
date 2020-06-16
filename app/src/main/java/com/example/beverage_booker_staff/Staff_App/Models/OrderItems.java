package com.example.beverage_booker_staff.Staff_App.Models;

public class OrderItems {

    private int orderID;

    private String Status;

    public OrderItems(int id, String status) {
        this.orderID = id;
        this.Status = status;

    }

    public int getOrderID() {
        return orderID;
    }

    public String getStatus() {
        return Status;
    }

    public int getOrderId() {
        return orderID;
    }
}
