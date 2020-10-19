package com.example.beverage_booker_staff.Staff_App.Activities;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
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

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InventoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InventoryAdapter inventoryAdapter;
    private ArrayList<MenuItem> menuItems;


    MenuItem itemClicked;
    String itemStock;
    int itemID;

    private EditText editTextInventory;
    private String itemType = "food";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        editTextInventory = findViewById(R.id.inventoryItemStock);

        recyclerView = findViewById(R.id.recyclerViewInventory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        menuItems = new ArrayList<>();
        inventoryAdapter = new InventoryAdapter(menuItems);
        recyclerView.setAdapter(inventoryAdapter);

        inventoryAdapter.setOnButtonClickListener(new InventoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                itemClicked = menuItems.get(position);
                itemID = itemClicked.getId();
                itemStock = itemClicked.getItemStock();
                updateInventoryItemStock();
            }
        });

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
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(InventoryActivity.this, t.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        });
    }

    private void updateInventoryItemStock() {


        System.out.println("Stock Int Update: " + itemStock);

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateInventoryItemStock(itemID, itemStock);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.success(InventoryActivity.this, "Inventory item updated", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();

                } else if (response.code() == 402) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(InventoryActivity.this, "Inventory item failed to update", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(InventoryActivity.this, t.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        });


    }
}