package com.example.beverage_booker_staff.Staff_App.Models;

public class Deliveries {

    private int userID;
    private int cartID;
    private String streetNumber;
    private String streetName;
    private int postCode;
    private String cityTown;

    public Deliveries(int userID, int cartID, String streetNumber, String streetName, int postCode, String cityTown) {

        this.userID = userID;
        this.cartID = cartID;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.postCode = postCode;
        this.cityTown = cityTown;
    }

    public int getUserID() {
        return userID;
    }

    public int getCartID() {
        return cartID;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getPostCode() {
        return postCode;
    }

    public String getCityTown() {
        return cityTown;
    }
}
