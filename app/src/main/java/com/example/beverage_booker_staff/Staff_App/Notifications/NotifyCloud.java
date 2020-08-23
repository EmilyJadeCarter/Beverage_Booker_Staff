package com.example.beverage_booker_staff.Staff_App.Notifications;



import android.util.Log;

import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Models.OrderItems;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotifyCloud {

    private static final String TAG = "MainActivity";

    public static void sendNotificationComplete(int userID){

        Log.d(TAG, "Was this even called?" + userID);

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .sendNotificationComplete(userID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 501) {

                    Log.d(TAG, "THIS FUCKING WORK");
                }
                if (response.code() == 502) {
                    Log.d(TAG, "THIS DIDN'T WORK");
                } else {

                    Log.d(TAG, String.valueOf(response.code()) + "This didn't work");

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });
    }
}