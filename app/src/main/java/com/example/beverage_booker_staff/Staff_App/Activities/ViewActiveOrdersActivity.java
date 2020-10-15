package com.example.beverage_booker_staff.Staff_App.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Adaptors.ViewActiveOrders;
import com.example.beverage_booker_staff.Staff_App.Models.OrderItems;
import com.example.beverage_booker_staff.Staff_App.Models.Staff;
import com.example.beverage_booker_staff.Staff_App.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ViewActiveOrdersActivity extends AppCompatActivity {

    public static final String ORDER_ID = "com.example.beverage_booker_staff.ORDER_ID";
    public static final String CART_ID = "com.example.beverage_booker_staff.CART_ID";
    public static final String ORDER_POSITION = "com.example.beverage_booker_staff.ORDER_POSITION";


    private ArrayList<OrderItems> mOrders;
    private RecyclerView mRecyclerView;
    private ViewActiveOrders mRecyclerAdapter;
    private Timer myTimer;
    private Staff activeStaff;
    private String orderID;
    private String cartID;
    private int assignedStaffID;
    private int activeStaffID;
    private int orderPosition;
    private int bodySize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activeStaff = SharedPrefManager.getInstance(ViewActiveOrdersActivity.this).getStaff();
        activeStaffID = activeStaff.getStaffID();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mOrders = new ArrayList<>();
        mRecyclerAdapter = new ViewActiveOrders(this, mOrders);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        //listener for start order
        mRecyclerAdapter.setOnItemClickListener(new ViewActiveOrders.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                myTimer.cancel(); //stops the timer so nothing happens during the onclick

                orderPosition = position;
                // prevents the indexoutofbounds error.
                if (mOrders.size() == bodySize) {
                    orderID = String.valueOf(mOrders.get(position).getOrderID());
                    cartID = String.valueOf(mOrders.get(position).getCartID());
                    assignedStaffID = mOrders.get(position).getAssignedStaff();
                    System.out.println("position: " + position);
                    System.out.println("Order ID: " + orderID);
                    System.out.println("Cart ID: " + cartID);
                    System.out.println("assignedStaff: " + assignedStaffID);
                    addToQueue();
                } else {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(ViewActiveOrdersActivity.this, "Please Tap Again", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                }
            }
        });

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateOrderList();
            }
        }, 0, 4000);
    }

    private final void updateOrderList() {
        Call<List<OrderItems>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getOrderList();

        call.enqueue(new Callback<List<OrderItems>>() {
            @Override
            public void onResponse(Call<List<OrderItems>> call, Response<List<OrderItems>> response) {
                if (response.code() == 200) {
                    mOrders.clear();
                    for (int i = 0; i < response.body().size(); i++) {
                        mOrders.add(response.body().get(i));
                        bodySize = response.body().size();
                    }
                    mRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<OrderItems>> call, Throwable t) {
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(ViewActiveOrdersActivity.this, t.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        });
    }

    private void addToQueue() {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .addToQueue(activeStaffID, orderID, cartID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201 && assignedStaffID == 0) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.success(ViewActiveOrdersActivity.this, "Order added to queue", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                    myTimer.cancel();
                    openOrder(orderID, cartID);
                } else if (response.code() == 402) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(ViewActiveOrdersActivity.this, "Failed to add order to queue", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                } else if (response.code() == 403 && (assignedStaffID == 1 || activeStaffID == assignedStaffID)) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.info(ViewActiveOrdersActivity.this, "Order already in queue - resuming order", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                    assignStaffToOrder();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(ViewActiveOrdersActivity.this, t.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        });
        return;
    }

    private void assignStaffToOrder() {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .assignStaffToOrder(activeStaffID, orderID, cartID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201 && (assignedStaffID == 1 || activeStaffID == assignedStaffID)) {
                    myTimer.cancel();
                    openOrder(orderID, cartID);
                } else if (response.code() == 402) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(ViewActiveOrdersActivity.this, "Staff member failed to be assigned to order", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(ViewActiveOrdersActivity.this, t.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        });
    }

    private void openOrder(String orderID, String cartID) {
        Intent intent = new Intent(this, ViewCartItemsActivity.class);
        intent.putExtra(ORDER_ID, orderID);
        intent.putExtra(CART_ID, cartID);
        intent.putExtra(ORDER_POSITION, orderPosition);
        startActivity(intent);
    }
}

