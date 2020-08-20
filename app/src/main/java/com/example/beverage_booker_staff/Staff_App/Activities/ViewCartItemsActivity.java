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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Adaptors.ViewActiveOrders;
import com.example.beverage_booker_staff.Staff_App.Adaptors.ViewCartItems;
import com.example.beverage_booker_staff.Staff_App.Models.CartItems;
import com.example.beverage_booker_staff.Staff_App.Models.OrderItems;
import com.example.beverage_booker_staff.Staff_App.Models.Staff;
import com.example.beverage_booker_staff.Staff_App.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ViewCartItemsActivity extends AppCompatActivity {

    private ArrayList<CartItems> mCartItems;
    private RecyclerView mRecyclerView;
    private ViewCartItems mRecyclerAdapter;
    private String orderNum;
    private String cartID;
    private Staff activeStaff = SharedPrefManager.getInstance(ViewCartItemsActivity.this).getStaff();
    private int activeStaffID;
    private int assignedStaffID;
    private Button completeOrderButton;
    private Button unassignOrderButton;
    Timer myTimer = new Timer();
    private boolean backButtonClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activeStaffID = activeStaff.getStaffID();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);
        Intent intent = getIntent();
        orderNum = intent.getStringExtra(ViewActiveOrdersActivity.ORDER_ID);
        cartID = intent.getStringExtra(ViewActiveOrdersActivity.CART_ID);

        myTimer.schedule(new TimerTask() {
                             @Override
                             public void run() {
                                 //activeChecker();
                             }
                         }, 0, 2000);

        TextView orderID = findViewById(R.id.orderID);

        orderID.setText(orderNum);

        mRecyclerView = findViewById(R.id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCartItems = new ArrayList<>();
        mRecyclerAdapter = new ViewCartItems(mCartItems);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        unassignOrderButton = findViewById(R.id.ButtonUnassignOrder);
        unassignOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonClicked = true;
                unassignStaff();
            }
        });

        mRecyclerAdapter.setOnItemClickListener(new ViewCartItems.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                String itemID = String.valueOf(mCartItems.get(position).getItemID());
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
                Toast.makeText(ViewCartItemsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(backButtonClicked != true){
            unassignStaff();
        }
    }

//    private void activeChecker() {
//        Call<List<OrderItems>> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .getOrderList();
//
//        call.enqueue(new Callback<List<OrderItems>>() {
//            @Override
//            public void onResponse(Call<List<OrderItems>> call, Response<List<OrderItems>> response) {
//                if (response.code() == 200) {
//                    for (int i = 0; i < response.body().size(); i++) {
//                        assignedStaffID = response.body().get(i).getAssignedStaff();
//                        if(assignedStaffID != 0) {
//                            if(assignedStaffID != 1) {
//                                if (activeStaffID != assignedStaffID) {
//                                    myTimer.cancel();
//                                    returnToOrders();
//                                }
//                            }
//                            System.out.println("it was 1" + assignedStaffID);
//                            return;
//                        }
//                        System.out.println("it was 0" + assignedStaffID);
//                        return;
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<OrderItems>> call, Throwable t) {
//                Toast.makeText(ViewCartItemsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }


    private void unassignStaff() {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .makeOrderAvailable(activeStaffID, orderNum, cartID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 402) {
                    Toast.makeText(ViewCartItemsActivity.this, "An error occurred when updating databases", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ViewCartItemsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Intent intent = new Intent(this, ViewActiveOrdersActivity.class);
        startActivity(intent);
    }

    private void returnToOrders() {
        Intent intent = new Intent(this, ViewActiveOrdersActivity.class);
        startActivity(intent);
        Toast.makeText(ViewCartItemsActivity.this, "Error: There is already someone on this order", Toast.LENGTH_LONG).show();
    }
}