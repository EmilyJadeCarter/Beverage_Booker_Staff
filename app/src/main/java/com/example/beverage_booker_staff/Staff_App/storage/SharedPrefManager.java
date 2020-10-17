package com.example.beverage_booker_staff.Staff_App.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.beverage_booker_staff.Staff_App.Models.Staff;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if(mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    //Get the user data and save it to shared preferences
    public void saveStaff(Staff staff) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", staff.getStaffID());
        editor.putInt("staffLevel", staff.getStaffLevel());
        editor.putString("firstName", staff.getFirstName());
        editor.putString("lastName", staff.getLastName());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.getInt("id", 0) != 0)
            return true;

        return false;
    }

    //Return the logged in user
    public Staff getStaff() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Staff(
                sharedPreferences.getInt("id", 0),
                sharedPreferences.getInt("staffLevel", 0),
                sharedPreferences.getString("firstName", null),
                sharedPreferences.getString("lastName", null)

        );
    }

    //Clear the logged in user when logging out
    public void clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
