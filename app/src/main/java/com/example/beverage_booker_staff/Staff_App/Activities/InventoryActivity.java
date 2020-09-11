package com.example.beverage_booker_staff.Staff_App.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Adaptors.InventoryAdapter;
import com.example.beverage_booker_staff.Staff_App.Models.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InventoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InventoryAdapter inventoryAdapter;
    private ArrayList<MenuItem> menuItems;
    MenuItem itemClicked;

    private String itemType = "food";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        recyclerView = findViewById(R.id.recyclerViewInventory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Update all button


        menuItems = new ArrayList<>();
        inventoryAdapter = new InventoryAdapter(menuItems);
        recyclerView.setAdapter(inventoryAdapter);

        Call<List<MenuItem>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getItemsForInventory(itemType);

        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if (response.code() == 200) {
                    for (int i = 0; i < response.body().size(); i++) {
                        menuItems.add(response.body().get(i));
                    }
                    inventoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                Toast.makeText(InventoryActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}