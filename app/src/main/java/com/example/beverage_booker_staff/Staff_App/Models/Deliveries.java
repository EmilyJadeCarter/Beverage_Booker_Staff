package com.example.beverage_booker_staff.Staff_App.Models;

public class Deliveries {

    private int userID;
    private String firstName;
    private String phone;
    private int cartID;
    private String streetUnit;
    private String streetName;


    public Deliveries(int userID, int cartID, String streetUnit, String streetName, int postCode, String cityTown) {

        this.userID = userID;
        this.cartID = cartID;
        this.streetUnit = streetUnit;
        this.streetName = streetName;

    }

    public int getUserID() {
        return userID;
    }

    public int getCartID() {
        return cartID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreetUnit() {
        return streetUnit;
    }

    public String getStreetName() {
        return streetName;
    }

}
