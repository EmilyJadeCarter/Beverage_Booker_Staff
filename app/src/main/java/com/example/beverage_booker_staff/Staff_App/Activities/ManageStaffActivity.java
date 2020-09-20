package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Adaptors.ManageStaffAdapter;
import com.example.beverage_booker_staff.Staff_App.Models.Staff;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageStaffActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ManageStaffAdapter manageStaffAdapter;
    private ArrayList<Staff> staffList;

    Button addStaffButton;

    Staff staffClicked;
    int staffID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);

        recyclerView = findViewById(R.id.recyclerViewStaff);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        staffList = new ArrayList<>();
        manageStaffAdapter = new ManageStaffAdapter(staffList);
        recyclerView.setAdapter(manageStaffAdapter);

        manageStaffAdapter.setOnButtonClickListener(new ManageStaffAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                staffClicked = staffList.get(position);
                staffID = staffClicked.getStaffID();
                deleteStaffMember();
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

        Call<List<Staff>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getStaff();

        call.enqueue(new Callback<List<Staff>>() {
            @Override
            public void onResponse(Call<List<Staff>> call, Response<List<Staff>> response) {
                if (response.code() == 200) {
                    for (int i = 0; i < response.body().size(); i++) {
                        staffList.add(response.body().get(i));
                    }
                    manageStaffAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Staff>> call, Throwable t) {
                Toast.makeText(ManageStaffActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        /*//Delete Staff Button
        deleteStaffButton = findViewById(R.id.deleteStaffButton);
        deleteStaffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDeleteStaff();
            }
        });*/

    }

    private void deleteStaffMember() {

        /*System.out.println("staffID Update: " + staffID);

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .deleteStaffMember(staffID);

             */

    }

    private void viewAddStaff() {
        Intent intent = new Intent(this, CreateStaffActivity.class);
        startActivity(intent);
    }
}