package com.example.beverage_booker_staff.Staff_App.API;


import com.example.beverage_booker_staff.Staff_App.Models.CartItems;
import com.example.beverage_booker_staff.Staff_App.Models.Deliveries;
import com.example.beverage_booker_staff.Staff_App.Models.LoginResponse;
import com.example.beverage_booker_staff.Staff_App.Models.OrderItems;
import com.example.beverage_booker_staff.Staff_App.Models.MenuItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    //Login existing staff member
    @FormUrlEncoded
    @POST("staffValidate")
    Call<LoginResponse> staffValidate(
            @Field("staffID") String id
    );

    //Get Orders list from database
    @GET("getorderslist")
    Call<List<OrderItems>> getOrderList();

    //Get Cart items from database
    @GET("getorderitems")
    Call<List<CartItems>> getOrderItems(
            @Query("cartID") int cartID
    );

    //Get Menu items from database
    @GET("getdeliverieslist")
    Call<List<Deliveries>> getDeliveriesList();

    //Mark Order as delivered
    @FormUrlEncoded
    @POST("markdelivered")
    Call<ResponseBody> markDelivered(
            @Field("userID") int userID,
            @Field("cartID") int cartID
    );

    //Get Menu items from database
    @GET("getitems")
    Call<List<MenuItem>> getItems();

    //Delete item from database
    @FormUrlEncoded
    @POST("deletemenuitem")
    Call<ResponseBody> deleteMenuItem(
            @Field("itemID") int itemID
    );

    //Adds order to queue
    @FormUrlEncoded
    @POST("addtoqueue")
    Call<ResponseBody> addToQueue(
            @Field("staffID") int staffID,
            @Field("orderID") String orderID,
            @Field("cartID") String cartID
    );

    //Make order available
    @FormUrlEncoded
    @POST("makeorderavailable")
    Call<ResponseBody> makeOrderAvailable(
            @Field("staffID") int staffID,
            @Field("orderID") String orderID,
            @Field("cartID") String cartID
    );

    //assign staff to order
    @FormUrlEncoded
    @POST("assignstafftoorder")
    Call<ResponseBody> assignStaffToOrder(
            @Field("staffID") int staffID,
            @Field("orderID") String orderID,
            @Field("cartID") String cartID
    );

    //add to completedOrders
    @FormUrlEncoded
    @POST("addcompletedorder")
    Call<ResponseBody> addCompletedOrder(
            @Field("orderID") String orderID
    );

    //add to completedOrders
    @FormUrlEncoded
    @POST("deleteorder")
    Call<ResponseBody> deleteOrder(
            @Field("orderID") String orderID,
            @Field("cartID") String cartID
    );

    //add to completedOrders
    @FormUrlEncoded
    @POST("deletestaffqueue")
    Call<ResponseBody> deleteStaffQueue(
            @Field("orderID") String orderID
    );

    //update cartitem status
    @FormUrlEncoded
    @POST("updatecartitemstatus")
    Call<ResponseBody> updateCartItemStatus(
            @Field("cartID") String cartID,
            @Field("itemID") String itemID,
            @Field("itemStatus") String itemStatus
    );
}
