package com.example.beverage_booker_staff.Staff_App.Models;

public class OrderItems {

    private int orderID;
    private int cartID;
    private String Status;
    private int assignedStaff;

    public OrderItems(int orderID, int cartID, String status, int assignedStaff) {
        this.orderID = orderID;
        this.cartID = cartID;
        this.Status = status;
        this.assignedStaff = assignedStaff;
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

    public int getAssignedStaff() {
        return assignedStaff;
    }
}
