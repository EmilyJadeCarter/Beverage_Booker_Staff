package com.example.beverage_booker_staff.Staff_App.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.Models.Staff;

import java.util.ArrayList;

public class ManageStaffActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Staff> staffList;

    private Button addStaffButton;
    private Button deleteStaffButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);

        recyclerView = findViewById(R.id.recyclerViewStaff);
        recyclerView.setHasFixedSize(true);

        staffList = new ArrayList<>();

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

    }
    private void viewAddStaff() {
        Intent intent = new Intent(this, CreateStaffActivity.class);
        startActivity(intent);
    }

    private void viewDeleteStaff() {
        //Intent intent = new Intent(this, ManageStaffActivity.class);
        //startActivity(intent);
    }
}