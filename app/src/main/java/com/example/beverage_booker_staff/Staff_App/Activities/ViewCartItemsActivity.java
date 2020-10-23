package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Adaptors.ViewCartItems;
import com.example.beverage_booker_staff.Staff_App.Models.CartItems;
import com.example.beverage_booker_staff.Staff_App.Models.OrderItems;
import com.example.beverage_booker_staff.Staff_App.Models.Staff;
import com.example.beverage_booker_staff.Staff_App.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCartItemsActivity extends AppCompatActivity {

    private ArrayList<CartItems> mCartItems;
    private RecyclerView mRecyclerView;
    private ViewCartItems mRecyclerAdapter;
    private String orderNum;
    private String cartID;
    private static String sCartID;
    private Staff activeStaff;
    private int activeStaffID;
    private int assignedStaffID;
    private Button completeOrderButton;
    private Button unassignOrderButton;
    private boolean backButtonClicked = false;
    private int orderPosition;
    private int itemStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activeStaff = SharedPrefManager.getInstance(ViewCartItemsActivity.this).getStaff();
        activeStaffID = activeStaff.getStaffID();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);
        Intent intent = getIntent();
        orderNum = intent.getStringExtra(ViewActiveOrdersActivity.ORDER_ID);
        cartID = intent.getStringExtra(ViewActiveOrdersActivity.CART_ID);
        sCartID = cartID;
        orderPosition = intent.getIntExtra(ViewActiveOrdersActivity.ORDER_POSITION, 0);

        activeChecker();

        TextView orderID = findViewById(R.id.orderID);

        orderID.setText(orderNum);

        mRecyclerView = findViewById(R.id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCartItems = new ArrayList<>();
        mRecyclerAdapter = new ViewCartItems(this, mCartItems);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        unassignOrderButton = findViewById(R.id.ButtonUnassignOrder);
        unassignOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unassignStaff();
            }
        });

        completeOrderButton = findViewById(R.id.button_Complete);
        completeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupConfirmationConfirmOrder();
            }
        });


        mRecyclerAdapter.setOnItemClickListener(new ViewCartItems.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                String itemID = String.valueOf(mCartItems.get(position).getItemID());
                itemStatus = Integer.valueOf(mCartItems.get(position).getItemStatus());
                System.out.println("itemStatus is " + itemStatus);
                System.out.println("position: " + position);
                System.out.println("Item ID: " + itemID);
            }
        });

        Call<List<CartItems>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getOrderItems(Integer.parseInt(cartID));

        call.enqueue(new Callback<List<CartItems>>() {
            @Override
            public void onResponse(Call<List<CartItems>> call, Response<List<CartItems>> response) {
                if (response.code() == 200) {
                    for (int i = 0; i < response.body().size(); i++) {
                        mCartItems.add(response.body().get(i));
                    }
                    mRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CartItems>> call, Throwable t) {
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(ViewCartItemsActivity.this, "Error while getting order's cart items", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    private void activeChecker() {
        Call<List<OrderItems>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getOrderList();

        call.enqueue(new Callback<List<OrderItems>>() {
            @Override
            public void onResponse(Call<List<OrderItems>> call, Response<List<OrderItems>> response) {
                if (response.code() == 200) {
                    assignedStaffID = response.body().get(orderPosition).getAssignedStaff();
                    if (assignedStaffID != 0) {
                        if (assignedStaffID != 1) {
                            if (activeStaffID != assignedStaffID) {
                                returnToOrders();
                            }
                        }
                        return;
                    }
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<OrderItems>> call, Throwable t) {
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(ViewCartItemsActivity.this, "Error while getting assigned staff member", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    private void unassignStaff() {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .makeOrderAvailable(activeStaffID, orderNum, cartID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 402) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(ViewCartItemsActivity.this, "An error occurred when updating databases", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(ViewCartItemsActivity.this, "Error while removing staff from order", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        backButtonClicked = true;
        Intent intent = new Intent(this, ViewActiveOrdersActivity.class);
        startActivity(intent);
    }

    private void returnToOrders() {
        Intent intent = new Intent(this, ViewActiveOrdersActivity.class);
        startActivity(intent);
    }

    public static String getCartID() {
        return sCartID;
    }

    private void popupConfirmationConfirmOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure the order is complete");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                returnToOrders();

                int cartID = Integer.parseInt(getCartID());
                System.out.println("Check : " + cartID);

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .updateOrderStatusToComplete(cartID);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            Toasty.Config.getInstance()
                                    .setTextSize(40)
                                    .apply();
                            Toast toast = Toasty.success(ViewCartItemsActivity.this, "Order Completed", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                            toast.show();

                        } else {
                            Toasty.Config.getInstance()
                                    .setTextSize(40)
                                    .apply();
                            Toast toast = Toasty.error(ViewCartItemsActivity.this, "There was a problem completing order", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toasty.Config.getInstance()
                                .setTextSize(40)
                                .apply();
                        Toast toast = Toasty.error(ViewCartItemsActivity.this, "Error while confirming order", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                        toast.show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
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

    @Override
    protected void onStop() {
        super.onStop();
        if (backButtonClicked == false && activeStaffID == assignedStaffID) {
            unassignStaff();
        }
    }
}