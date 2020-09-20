package com.example.beverage_booker_staff.Staff_App.Models;

public class Staff {

    private int staffID, staffLevel;
    private String firstName, lastName;


    public Staff(int staffID, int staffLevel, String firstName, String lastName) {
        this.staffID = staffID;
        this.staffLevel = staffLevel;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getStaffID() {
        return staffID;
    }

    public int getStaffLevel() {
        return staffLevel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
