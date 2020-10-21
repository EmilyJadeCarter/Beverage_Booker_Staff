package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Adaptors.DeliveriesAdapter;
import com.example.beverage_booker_staff.Staff_App.Models.Deliveries;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveriesActivity extends AppCompatActivity {

    private ArrayList<Deliveries> mDeliveries;
    private DeliveriesAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private Button deliveredButton;

    private Timer deliveryTimer;

    private int bodySize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveries);

        mRecyclerView = findViewById(R.id.recyclerView3);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDeliveries = new ArrayList<>();
        mAdapter = new DeliveriesAdapter(mDeliveries);
        mRecyclerView.setAdapter(mAdapter);

        updateDeliveriesList();
        createTimer();
        //Delivered Button
        mAdapter.setOnItemClickListener(new DeliveriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                deliveryTimer.cancel();

                if (mDeliveries.size() == bodySize) {
                    int userID = mDeliveries.get(position).getUserID();
                    System.out.println(userID);
                    int cartID = mDeliveries.get(position).getCartID();
                    System.out.println(cartID);
                    markOrderDelivered(userID, cartID);
                } else {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(DeliveriesActivity.this, "Please Tap Again", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                }
                createTimer();
            }
        });
    }

    private void createTimer() {
        deliveryTimer = new Timer();
        deliveryTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateDeliveriesList();
            }
        }, 0, 2000);
    }

    private final void updateDeliveriesList() {
        Call<List<Deliveries>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDeliveriesList();

        call.enqueue(new Callback<List<Deliveries>>() {

            @Override
            public void onResponse(Call<List<Deliveries>> call, Response<List<Deliveries>> response) {
                if (response.code() == 200) {
                    mDeliveries.clear();
                    for (int i = 0; i < response.body().size(); i++) {
                        mDeliveries.add(response.body().get(i));
                        bodySize = response.body().size();
                    }

                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Deliveries>> call, Throwable t) {
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(DeliveriesActivity.this, "Error while updating deliveries list", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    private void markOrderDelivered(int userID, int cartID) {

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .markDelivered(userID, cartID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 201) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.success(DeliveriesActivity.this, "Order Delivered", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();

                } else if (response.code() == 422) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(DeliveriesActivity.this, "Marking order delivered failed", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(DeliveriesActivity.this, "Error while marking order delivered", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }
}