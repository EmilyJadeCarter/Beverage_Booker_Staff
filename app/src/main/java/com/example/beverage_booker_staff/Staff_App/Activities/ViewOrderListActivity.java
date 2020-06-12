package com.example.beverage_booker_staff.Staff_App.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.Toast;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Adaptors.RecyclerAdapter;
import com.example.beverage_booker_staff.Staff_App.Models.MenuItem;
import com.example.beverage_booker_staff.Staff_App.Models.OrderItems;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderListActivity extends AppCompatActivity implements RecyclerAdapter.OnClickListener {

    private RecyclerAdapter mRecyclerAdapter;
    private ArrayList<OrderItems> mOrders;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mOrders = new ArrayList<>();
        mRecyclerAdapter = new RecyclerAdapter(mOrders);

        Call<List<OrderItems>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getOrderItems();

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
                Toast.makeText(ViewOrderListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });








//        mRecyclerView.setAdapter(mRecyclerAdapter);
//        //Listener for Add to Cart
//        mRecyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//            int orderID = mOrders.get(position).getId();
//
//            @Override
//            public void onItemClick(int position) {
//
//            }
//
//
//
//        });
//    }

//    @Override
//    public void onItemClick(int position) {
//
//    }
    }

    @Override
    public void onItemClick(int position) {

    }
}