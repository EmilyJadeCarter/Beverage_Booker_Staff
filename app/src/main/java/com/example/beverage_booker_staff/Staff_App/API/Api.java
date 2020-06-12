package com.example.beverage_booker_staff.Staff_App.API;


import com.example.beverage_booker_staff.Staff_App.Models.LoginResponse;
import com.example.beverage_booker_staff.Staff_App.Models.OrderItems;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    //Login existing staff member
    @FormUrlEncoded
    @POST("staffValidate")
    Call<LoginResponse> staffValidate(
            @Field("staffID") String id
    );

    //Get Menu items from database
    @GET("getorderitems")
    Call<List<OrderItems>> getOrderItems();
}
