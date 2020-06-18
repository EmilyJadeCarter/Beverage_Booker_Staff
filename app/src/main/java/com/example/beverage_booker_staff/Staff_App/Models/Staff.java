package com.example.beverage_booker_staff.Staff_App.Models;

public class Staff {

    private int staffID, level;
    private String firstName, LastName;


    public Staff(int staffID, int level, String firstName, String lastName) {
        this.staffID = staffID;
        this.level = level;
        this.firstName = firstName;
        LastName = lastName;
    }

    public int getStaffID() {
        return staffID;
    }

    public int getLevel() {
        return level;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return LastName;
    }

}
