package com.example.beverage_booker_staff.Staff_App.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.beverage_booker_staff.R;

public class MainMenuActivity extends AppCompatActivity {

    private Button OrdersButton;
    private Button DeliveriesButton;

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
    }

    private void viewActiveOrders() {
        Intent intent = new Intent(this, ViewActiveOrdersActivity.class );
        startActivity(intent);
    }

    private void viewDeliveries() {
        Intent intent = new Intent(this, DeliveriesActivity.class );
        startActivity(intent);
    }
}