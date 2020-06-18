package com.example.beverage_booker_staff.Staff_App.Models;

public class LoginResponse {

    private boolean error;
    private String message;
    private Staff staff;


    public LoginResponse(boolean error, String message, Staff staff) {
        this.error = error;
        this.message = message;
        this.staff = staff;
    }


    public boolean isError() {
        return error;
    }


    public String getMessage() {
        return message;
    }


    public Staff getStaff() {
        return staff;
    }
}
