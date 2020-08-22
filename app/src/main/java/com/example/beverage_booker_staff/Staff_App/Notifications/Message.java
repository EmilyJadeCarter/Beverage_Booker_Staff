package com.example.beverage_booker_staff.Staff_App.Notifications;



import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;


class Message {

    public void sendNotificationComplete(int userID){

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .sendNotificationComplete(userID);

    }













//
//        // This registration token comes from the client FCM SDKs.
//        String registrationToken = "YOUR_REGISTRATION_TOKEN";
//
//// See documentation on defining a message payload.
//        Message message = Message.builder()
//                .putData("score", "850")
//                .putData("time", "2:45")
//                .setToken(registrationToken)
//                .build();
//
//// Send a message to the device corresponding to the provided
//// registration token.
//        String response = FirebaseMessaging.getInstance().send(message);
//// Response is a message ID string.
//        System.out.println("Successfully sent message: " + response);
//    }
//
//    private static Object builder() {
//








}




