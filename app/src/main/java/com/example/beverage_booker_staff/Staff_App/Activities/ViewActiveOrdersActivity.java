package com.example.beverage_booker_staff.Staff_App.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Adaptors.ViewActiveOrders;
import com.example.beverage_booker_staff.Staff_App.Models.OrderItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ViewActiveOrdersActivity extends AppCompatActivity implements ViewActiveOrders.OnItemClickListener {

    public static final String ORDER_ID = "com.example.beverage_booker_staff.ORDER_ID";
    public static final String CART_ID = "com.example.beverage_booker_staff.CART_ID";


    private ArrayList<OrderItems> mOrders;
    private RecyclerView mRecyclerView;
    private ViewActiveOrders mRecyclerAdapter;

    private Timer myTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mOrders = new ArrayList<>();
        mRecyclerAdapter = new ViewActiveOrders(mOrders);
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

                String orderID = String.valueOf(mOrders.get(position).getOrderID());
                String cartID = String.valueOf(mOrders.get(position).getCartID());
                System.out.println("position: " + position);
                System.out.println("Item ID: " + orderID);
                openOrder(orderID, cartID);
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

        }, 0, 5000);
    }

    @Override
    public void onItemClick(int position) {
    }


    private void openOrder(String orderID, String cartID) {

        Intent intent = new Intent(this, ViewCartItemsActivity.class);
        intent.putExtra(ORDER_ID, orderID);
        intent.putExtra(CART_ID, cartID);
        startActivity(intent);
    }
}


