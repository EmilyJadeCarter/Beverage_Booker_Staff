package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beverage_booker_staff.R;

public class MainMenuActivity extends AppCompatActivity {

    private Button OrdersButton;
    private Button DeliveriesButton;
    private Button MenuButton;
    private Button inventoryButton;
    private Button addStaffButton;
    private Button deleteStaffButton;
    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Orders Button
        OrdersButton = findViewById(R.id.OrdersButton);
        OrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewActiveOrders();
            }
        });

        //Deliveries Button
        DeliveriesButton = findViewById(R.id.DeliveriesButton);
        DeliveriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDeliveries();
            }
        });

        //Menu Button
        MenuButton = findViewById(R.id.MenuButton);
        MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMenu();
            }
        });

        //Inventory Button
        inventoryButton = findViewById(R.id.inventoryButton);
        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewInventory();
            }
        });

        //Add Staff Button
        addStaffButton = findViewById(R.id.addStaffButton);
        addStaffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAddStaff();
            }
        });

        //Delete Staff Button
        deleteStaffButton = findViewById(R.id.deleteStaffButton);
        deleteStaffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDeleteStaff();
            }
        });

        //Sign Out Button
        signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void viewActiveOrders() {
        Intent intent = new Intent(this, ViewActiveOrdersActivity.class);
        startActivity(intent);
    }

    private void viewDeliveries() {
        Intent intent = new Intent(this, DeliveriesActivity.class);
        startActivity(intent);
    }

    private void viewMenu() {
        Intent intent = new Intent(this, BrowseMenuActivity.class);
        startActivity(intent);
    }

    private void viewInventory() {
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }

    private void viewAddStaff() {
        Intent intent = new Intent(this, CreateStaffActivity.class);
        startActivity(intent);
    }
    //Not in my implementation
    private void viewDeleteStaff() {
        //Intent intent = new Intent(this, NAMEOFACTIVITYHERE.class);
        //startActivity(intent);
    }

    private void signOut() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}