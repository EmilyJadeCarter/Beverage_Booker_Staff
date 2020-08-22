package com.example.beverage_booker_staff.Staff_App.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
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
    private Staff activeStaff = SharedPrefManager.getInstance(ViewActiveOrdersActivity.this).getStaff();
    private String orderID;
    private String cartID;
    private int assignedStaffID;
    private int activeStaffID;
    private int orderPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activeStaffID = activeStaff.getStaffID();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mOrders = new ArrayList<>();
        mRecyclerAdapter = new ViewActiveOrders(this, mOrders);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mOrders.clear();

                //listener for start order
                mRecyclerAdapter.setOnItemClickListener(new ViewActiveOrders.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        orderPosition = position;

                        orderID = String.valueOf(mOrders.get(position).getOrderID());
                        cartID = String.valueOf(mOrders.get(position).getCartID());
                        assignedStaffID = mOrders.get(position).getAssignedStaff();
                        System.out.println("position: " + position);
                        System.out.println("Order ID: " + orderID);
                        System.out.println("Cart ID: " + cartID);
                        System.out.println("assignedStaff: " + assignedStaffID);
                        addToQueue();
                    }
                });

                Call<List<OrderItems>> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .getOrderList();

                call.enqueue(new Callback<List<OrderItems>>() {
                    @Override
                    public void onResponse(Call<List<OrderItems>> call, Response<List<OrderItems>> response) {
                        if (response.code() == 200) {
                            for (int i = 0; i < response.body().size(); i++) {
                                mOrders.add(response.body().get(i));
                            }
                            mRecyclerAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<OrderItems>> call, Throwable t) {
                        Toast.makeText(ViewActiveOrdersActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

        }, 0, 3000);
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
                    Toast.makeText(ViewActiveOrdersActivity.this, "Order added to queue", Toast.LENGTH_LONG).show();
                    openOrder(orderID, cartID);
                } else if (response.code() == 402) {
                    Toast.makeText(ViewActiveOrdersActivity.this, "Failed to add order to queue", Toast.LENGTH_LONG).show();
                } else if (response.code() == 403 && (assignedStaffID == 1 || activeStaffID == assignedStaffID)) {
                    Toast.makeText(ViewActiveOrdersActivity.this, "Order already in queue - resuming order", Toast.LENGTH_LONG).show();
                    assignStaffToOrder();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ViewActiveOrdersActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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
                    openOrder(orderID, cartID);
                } else if (response.code() == 402) {
                    Toast.makeText(ViewActiveOrdersActivity.this, "Staff member failed to be assigned to order", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ViewActiveOrdersActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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

