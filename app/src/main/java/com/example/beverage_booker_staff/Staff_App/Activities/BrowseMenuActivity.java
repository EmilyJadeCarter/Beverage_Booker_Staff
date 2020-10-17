package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Adaptors.RecyclerAdapter;
import com.example.beverage_booker_staff.Staff_App.Models.MenuItem;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseMenuActivity extends AppCompatActivity {
    public static final String ITEM_ID = "com.example.beverage_booker_staff.ITEM_ID";
    public static final String ITEM_TITLE = "com.example.beverage_booker_staff.ITEM_TITLE";
    public static final String ITEM_DESC = "com.example.beverage_booker_staff.ITEM_DESC";
    public static final String ITEM_PRICE = "com.example.beverage_booker_staff.ITEM_PRICE";
    public static final String ITEM_MILK = "com.example.beverage_booker_staff.ITEM_MILK";
    public static final String ITEM_SUGAR = "com.example.beverage_booker_staff.ITEM_SUGAR";
    public static final String ITEM_DECAF = "com.example.beverage_booker_staff.ITEM_DECAF";
    public static final String ITEM_EXTRAS = "com.example.beverage_booker_staff.ITEM_EXTRAS";
    public static final String ITEM_FRAPPE = "com.example.beverage_booker_staff.ITEM_FRAPPE";
    public static final String ITEM_HEATED = "com.example.beverage_booker_staff.ITEM_HEATED";
    public static final String ITEM_TYPE = "com.example.beverage_booker_staff.ITEM_TYPE";
    public static final String ITEM_TIME = "com.example.beverage_booker_staff.ITEM_TIME";

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<MenuItem> menuItems;
    MenuItem itemClicked;

    // menu item stuff
    int itemID;
    String itemTitle;
    String itemDesc;
    double itemPrice;
    int itemMilk;
    int itemSugar;
    int itemDecaf;
    int itemExtras;
    int itemFrappe;
    int itemHeated;
    String itemType;
    int itemTime;

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
                itemDesc = itemClicked.getDescription();
                itemPrice = itemClicked.getPrice();
                itemMilk = itemClicked.getMilk();
                itemSugar = itemClicked.getSugar();
                itemDecaf = itemClicked.getDecaf();
                itemExtras = itemClicked.getExtras();
                itemFrappe = itemClicked.getFrappe();
                itemHeated = itemClicked.getHeated();
                itemType = itemClicked.getItemType();
                itemTime = itemClicked.getItemTime();
                if (selection == 1) {
                    MenuItemFormModify();
                } else if (selection == 2) {
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
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(BrowseMenuActivity.this, t.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        });
    }

    private void MenuItemFormAdd() {
        Intent intent = new Intent(this, ItemTypeSelectionActivity.class);
        startActivity(intent);
    }

    // sends all item information via the intent
    private void MenuItemFormModify() {
        Intent intent = new Intent(this, ItemFormActivity.class);
        intent.putExtra(ITEM_ID, itemID);
        intent.putExtra(ITEM_TITLE, itemTitle);
        intent.putExtra(ITEM_DESC, itemDesc);
        intent.putExtra(ITEM_PRICE, itemPrice);
        intent.putExtra(ITEM_MILK, itemMilk);
        intent.putExtra(ITEM_SUGAR, itemSugar);
        intent.putExtra(ITEM_DECAF, itemDecaf);
        intent.putExtra(ITEM_EXTRAS, itemExtras);
        intent.putExtra(ITEM_FRAPPE, itemFrappe);
        intent.putExtra(ITEM_HEATED, itemHeated);
        intent.putExtra(ITEM_TYPE, itemType);
        intent.putExtra(ITEM_TIME, itemTime);
        startActivity(intent);
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
                            Toasty.Config.getInstance()
                                    .setTextSize(40)
                                    .apply();
                            Toast toast = Toasty.success(BrowseMenuActivity.this, "Item Deleted", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                            toast.show();
                            Intent intent = getIntent();
                            menuItems.clear();
                            finish();
                            startActivity(intent);
                        } else if (response.code() == 402) {
                            Toasty.Config.getInstance()
                                    .setTextSize(40)
                                    .apply();
                            Toast toast = Toasty.error(BrowseMenuActivity.this, "Item Failed To Delete", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toasty.Config.getInstance()
                                .setTextSize(40)
                                .apply();
                        Toast toast = Toasty.error(BrowseMenuActivity.this, t.getMessage(), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                        toast.show();
                    }
                });
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
