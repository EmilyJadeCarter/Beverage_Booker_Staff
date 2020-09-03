package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.Adaptors.RecyclerAdapter;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Models.MenuItem;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseMenuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<MenuItem> menuItems;
    MenuItem itemClicked;
    int itemID;
    String itemTitle;
    int milkstat;

    private Button addMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_menu_items);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //add menu item button
        addMenuItem = findViewById(R.id.addMenuItem);
        addMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuItemFormAdd();
            }

        });

        menuItems = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(menuItems);
        recyclerView.setAdapter(recyclerAdapter);

        //modify and delete buttons
        recyclerAdapter.setOnButtonClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int selection, int position) {
                itemClicked = menuItems.get(position);
                itemID = menuItems.get(position).getId();
                itemTitle = itemClicked.getName();
                milkstat = itemClicked.getMilk(); //works which means everything else will work

                if(selection == 1) {
                    MenuItemFormModify();
                }
                else if(selection == 2){
                    popupConfirmationOfDeletion();
                }
            }
        });

        Call<List<MenuItem>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getItemsForStaffMenu();

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

    private void MenuItemFormAdd(){
        //TODO Implement Add Item Form
    }

    private void MenuItemFormModify(){
        //TODO Implement Modify Item Form (same as add but with preexisting data)
        // would probably be best to use intents to send the data of the item clicked
        // data is attainable from the lines 64 stuff.
    }

    // This is a popup menu that allows the user to confirm a deletion of an item, they can cancel as well
    private void popupConfirmationOfDeletion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("You are about to delete " + itemTitle + " from the database");
        builder.setCancelable(false);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Here is where the program interacts with the database to delete an item
                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .deleteMenuItem(itemID);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 201) {
                            Toast.makeText(BrowseMenuActivity.this, "Item Deleted", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 402) {
                            Toast.makeText(BrowseMenuActivity.this, "Item Failed To Delete", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(BrowseMenuActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                // Here the program refreshes the activity allowing for the refreshed version of items when a user deletes one.
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }
}
