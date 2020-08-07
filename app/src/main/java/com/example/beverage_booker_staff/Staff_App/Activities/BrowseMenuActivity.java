package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.Adaptors.RecyclerAdapter;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Models.MenuItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseMenuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<MenuItem> menuItems;

    //View Cart Button
    private Button addMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_menu_items);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //View Cart Button
        addMenuItem = findViewById(R.id.addMenuItem);
        addMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenuItemForm();
            }

        });

        menuItems = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(menuItems);
        recyclerView.setAdapter(recyclerAdapter);

        //Listener for modify and delete buttons
        recyclerAdapter.setOnButtonClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int selection) {
                if(selection == 1) {
                    GoToDelivery(); //mod men item, FOR TESTING
                }
                else if(selection == 2){
                    GoToMainMenu(); //dete men item, FOR TESTING
                }
            }
        });

        Call<List<MenuItem>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getItems();

        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if (response.code() == 200) {
                    for (int i = 0; i < response.body().size(); i++) {
                        menuItems.add(response.body().get(i));
                    }
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                Toast.makeText(BrowseMenuActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // This is where a user will add a new item
    private void openMenuItemForm() {
        //Intent intent = new Intent(this, MenuItemFormActivity.class );
        //startActivity(intent);
    }

    //FOR TESTING
    private void GoToDelivery(){
        Intent intent = new Intent(this, DeliveriesActivity.class );
        startActivity(intent);
    }

    //FOR TESTING
    private void GoToMainMenu(){
        Intent intent = new Intent(this, MainMenuActivity.class );
        startActivity(intent);
    }
}
