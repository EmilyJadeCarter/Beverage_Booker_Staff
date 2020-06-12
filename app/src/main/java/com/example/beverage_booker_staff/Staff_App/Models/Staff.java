package com.example.beverage_booker_staff.Staff_App.Models;

public class Staff {

    private int id, level;
    private String firstName, LastName;


    public Staff(int id, int level, String firstName, String lastName) {
        this.id = id;
        this.level = level;
        this.firstName = firstName;
        LastName = lastName;
    }

    public int getId() {
        return id;
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
