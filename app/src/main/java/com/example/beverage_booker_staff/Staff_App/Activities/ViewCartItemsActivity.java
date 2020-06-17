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
import com.example.beverage_booker_staff.Staff_App.Adaptors.ViewCartItems;
import com.example.beverage_booker_staff.Staff_App.Models.CartItems;
import com.example.beverage_booker_staff.Staff_App.Models.OrderItems;

import java.util.ArrayList;
import java.util.List;

public class ViewCartItemsActivity extends AppCompatActivity {

    private ArrayList<CartItems> mCartItems;
    private RecyclerView mRecyclerView;
    private ViewCartItems mRecyclerAdapter;
    private String orderNum;
    private String cartID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);

        Intent intent = getIntent();
        orderNum = intent.getStringExtra(ViewActiveOrdersActivity.ORDER_ID);
        cartID = intent.getStringExtra(ViewActiveOrdersActivity.CART_ID);
        System.out.println(cartID);

        TextView orderID = findViewById(R.id.orderID);

        orderID.setText(orderNum);

        mRecyclerView = findViewById(R.id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCartItems = new ArrayList<>();
        mRecyclerAdapter = new ViewCartItems(mCartItems);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mRecyclerAdapter.setOnItemClickListener(new ViewCartItems.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                String itemID = String.valueOf(mCartItems.get(position).getItemID());
                System.out.println("position: " + position);
                System.out.println("Item ID: " + itemID);
                checkItemOff();

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
                Toast.makeText(ViewCartItemsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void checkItemOff() {
        System.out.println("This item has been checked");
    }
}